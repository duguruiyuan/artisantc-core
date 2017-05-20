package cn.artisantc.core.service;

import cn.artisantc.core.exception.AdvertisementNotFoundException;
import cn.artisantc.core.exception.UploadAdvertisementImageFailureException;
import cn.artisantc.core.persistence.entity.Administrator;
import cn.artisantc.core.persistence.entity.Advertisement;
import cn.artisantc.core.persistence.entity.AdvertisementBrowseRecord;
import cn.artisantc.core.persistence.entity.AdvertisementImage;
import cn.artisantc.core.persistence.entity.User;
import cn.artisantc.core.persistence.helper.UserHelper;
import cn.artisantc.core.persistence.repository.AdministratorRepository;
import cn.artisantc.core.persistence.repository.AdvertisementBrowseRecordRepository;
import cn.artisantc.core.persistence.repository.AdvertisementImageRepository;
import cn.artisantc.core.persistence.repository.AdvertisementRepository;
import cn.artisantc.core.persistence.repository.OAuth2Repository;
import cn.artisantc.core.persistence.specification.AdvertisementSpecification;
import cn.artisantc.core.util.DateTimeUtil;
import cn.artisantc.core.util.ImageUtil;
import cn.artisantc.core.util.LoginUserUtil;
import cn.artisantc.core.util.PageUtil;
import cn.artisantc.core.web.rest.v1_0.vo.APIErrorResponse;
import cn.artisantc.core.web.rest.v1_0.vo.AdvertisementDetailView;
import cn.artisantc.core.web.rest.v1_0.vo.AdvertisementImageView;
import cn.artisantc.core.web.rest.v1_0.vo.AdvertisementView;
import cn.artisantc.core.web.rest.v1_0.vo.InitialPreviewConfigView;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.AdvertisementViewPaginationList;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * “AdvertisementService”接口的实现类。
 * Created by xinjie.li on 2016/11/4.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Service
@Transactional
public class AdvertisementServiceImpl implements AdvertisementService {

    private static final Logger LOG = LoggerFactory.getLogger(AdvertisementServiceImpl.class);

    private AdvertisementRepository advertisementRepository;

    private AdvertisementImageRepository advertisementImageRepository;

    private OAuth2Repository oAuth2Repository;

    private AdministratorRepository administratorRepository;

    private AdvertisementBrowseRecordRepository advertisementBrowseRecordRepository;

    @Autowired
    public AdvertisementServiceImpl(AdvertisementRepository advertisementRepository, AdvertisementImageRepository advertisementImageRepository,
                                    OAuth2Repository oAuth2Repository, AdministratorRepository administratorRepository,
                                    AdvertisementBrowseRecordRepository advertisementBrowseRecordRepository) {
        this.advertisementRepository = advertisementRepository;
        this.advertisementImageRepository = advertisementImageRepository;
        this.oAuth2Repository = oAuth2Repository;
        this.administratorRepository = administratorRepository;
        this.advertisementBrowseRecordRepository = advertisementBrowseRecordRepository;
    }

    @Override
    public AdvertisementViewPaginationList findByPage(int page, int pageSize) {
        AdvertisementViewPaginationList paginationList = new AdvertisementViewPaginationList();

        if (pageSize <= 0) {
            pageSize = PageUtil.ADVERTISEMENT_PAGE_SIZE;
        }
        PageUtil pageUtil = new PageUtil(pageSize);// 设置每页的记录数
        page = pageUtil.getPageForPageable(page);

        Pageable pageable = new PageRequest(page, pageUtil.getPageSize());
        Page<Advertisement> advertisementPage = advertisementRepository.findAll(AdvertisementSpecification.findAllWithDefaultOrder(), pageable);
        List<Advertisement> advertisementList = advertisementPage.getContent();

        paginationList.setTotalPages(String.valueOf(advertisementPage.getTotalPages()));
        paginationList.setTotalRecords(String.valueOf(advertisementPage.getTotalElements()));

        // 下一页的页数，这里"+1"的目的是还原当前的页数，而不是查询结果里的页数
        int nextPage = advertisementPage.getNumber() + 1;

        // 如果还有下一页，则继续"+1"返回下一页的页数
        if (advertisementPage.hasNext()) {
            nextPage = nextPage + 1;
        }
        LOG.debug("advertisementPage.getNumber(): {}", advertisementPage.getNumber());
        LOG.debug("advertisementPage.getNumberOfElements(): {}", advertisementPage.getNumberOfElements());
        paginationList.setNextPage(String.valueOf(nextPage));

        // 遍历查询结果列表，并进行类型转换
        if (null != advertisementList && !advertisementList.isEmpty()) {
            List<AdvertisementView> advertisementViews = new ArrayList<>();
            for (Advertisement advertisement : advertisementList) {
                AdvertisementView view = new AdvertisementView();
                view.setTitle(advertisement.getTitle());
                view.setId(String.valueOf(advertisement.getId()));
                view.setBrowseTimes(String.valueOf(advertisementBrowseRecordRepository.countByAdvertisement_id(advertisement.getId())));// 广告的浏览次数
                if (null != advertisement.getImages() && !advertisement.getImages().isEmpty()) {
                    String url;
                    try {
                        url = ImageUtil.getAdvertisementImageUrlPrefix() + advertisement.getImages().iterator().next().getImageName();
                        view.setCoverUrl(url);// 广告的封面图片
                    } catch (ConfigurationException e) {
                        LOG.error(e.getMessage(), e);
                    }
                }
                advertisementViews.add(view);
            }

            paginationList.getAdvertisementViews().addAll(advertisementViews);
        }

        return paginationList;
    }

    @Override
    public AdvertisementDetailView findById(String id) {
        if (StringUtils.isBlank(id) || !NumberUtils.isParsable(id)) {
            throw new AdvertisementNotFoundException();
        }
        Advertisement advertisement = advertisementRepository.findOne(NumberUtils.toLong(id));
        if (null == advertisement) {
            throw new AdvertisementNotFoundException();
        }

        AdvertisementDetailView view = new AdvertisementDetailView();
        view.setId(String.valueOf(advertisement.getId()));
        view.setCreateDateTime(DateTimeUtil.getPrettyDescription(advertisement.getCreateDateTime()));
        view.setBrowseTimes(String.valueOf(advertisementBrowseRecordRepository.countByAdvertisement_id(advertisement.getId())));// 广告的浏览次数
        view.setTitle(advertisement.getTitle());
        if (null != advertisement.getImages() && !advertisement.getImages().isEmpty()) {
            String url;
            try {
                url = ImageUtil.getAdvertisementImageUrlPrefix() + advertisement.getImages().iterator().next().getImageName();
                view.setCoverUrl(url);// 广告的封面图片
            } catch (ConfigurationException e) {
                LOG.error(e.getMessage(), e);
            }
        }

        // 记录广告的浏览记录
        AdvertisementBrowseRecord browseRecord = new AdvertisementBrowseRecord();
        browseRecord.setAdvertisement(advertisement);

        Date date = new Date();// 生成时间，保证该数据需要用到时间的地方的数据一致性
        browseRecord.setCreateDateTime(date);
        browseRecord.setUpdateDateTime(date);

        // 校验当前登录用户信息
        try {
            User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
            if (null != user) {
                browseRecord.setUser(user);
            }
        } catch (UsernameNotFoundException e) {
            LOG.debug("未登录用户访问了ID为 {} 的广告", id);
        }

        advertisementBrowseRecordRepository.save(browseRecord);// 保存浏览记录

        return view;
    }

    @Override
    public void create(String title, String imageName, String imageWidth, String imageHeight) {
        int imageWidthInt = ImageUtil.ADVERTISEMENT_IMAGE_WIDTH;
        int imageHeightInt = 600;

        if (StringUtils.isNotBlank(imageWidth) && !NumberUtils.isParsable(imageWidth)) {
            imageWidthInt = NumberUtils.toInt(imageWidth);
        }
        if (StringUtils.isNotBlank(imageHeight) && !NumberUtils.isParsable(imageHeight)) {
            imageHeightInt = NumberUtils.toInt(imageHeight);
        }

        // 构建数据
        Advertisement advertisement = new Advertisement();

        List<AdvertisementImage> advertisementImages = new ArrayList<>();
        AdvertisementImage image = new AdvertisementImage();
        Date date = new Date();
        image.setCreateDateTime(date);
        image.setUpdateDateTime(date);
        image.setAdvertisement(advertisement);
        image.setImageName(imageName);
        image.setImageWidth(imageWidthInt);
        image.setImageHeight(imageHeightInt);

        image = advertisementImageRepository.save(image);
        advertisementImages.add(image);
        advertisement.setImages(advertisementImages);

        advertisement.setCreateDateTime(date);
        advertisement.setUpdateDateTime(date);
        advertisement.setStatus(Advertisement.Status.PUBLISHED);// TODO: 2016/11/29 设置状态
        advertisement.setCategory(Advertisement.Category.INFORMATION);// TODO: 2016/11/29 设置类型
        if (StringUtils.isNotBlank(title)) {
            advertisement.setTitle(title);
        }

        advertisementRepository.save(advertisement);
    }

    @Override
    public AdvertisementImageView uploadAdvertisementImage(MultipartFile[] images) {
        String url = "";

        // 校验“上传图片”
        List<MultipartFile> uploadedFiles = new ArrayList<>();// 真正提交过来准备上传的图片
        if (null == images) {
            throw new UploadAdvertisementImageFailureException(APIErrorResponse.ErrorCode.E010114.getErrorCode());
        } else {
            for (MultipartFile file : images) {
                if (!file.isEmpty()) {
                    if (!ImageUtil.isPicture(file)) {
                        throw new UploadAdvertisementImageFailureException(APIErrorResponse.ErrorCode.E990031.getErrorCode());
                    }
                    uploadedFiles.add(file);
                }
            }
        }

        // 校验“上传图片”的个数，目前只允许上传1个图片
        if (uploadedFiles.size() > APIErrorResponse.MAX_ADVERTISEMENT_IMAGE_LENGTH) {
            APIErrorResponse.ErrorCode.E990032.setArgs(new String[]{String.valueOf(APIErrorResponse.MAX_ADVERTISEMENT_IMAGE_LENGTH)});
            throw new UploadAdvertisementImageFailureException(APIErrorResponse.ErrorCode.E990032.getErrorCode());
        }

        // 校验当前登录管理员信息
        Administrator administrator = administratorRepository.findByUsername(LoginUserUtil.getLoginUsername());
        if (null == administrator) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        List<File> toDeleteFiles = new ArrayList<>();// “待删除文件列表”，用于记录过程中产生的临时文件，在完成后删除掉
        // 准备水印图片
//        BufferedImage watermarkImage;
//        try {
//            watermarkImage = ImageIO.read(new File(ImageUtil.getWatermarkStorePath()));
//        } catch (IOException | ConfigurationException e) {
//            LOG.error(e.getMessage(), e);
//            throw new UploadAdvertisementImageFailureException(APIErrorResponse.ErrorCode.E990030.getErrorCode());
//        }

        // 遍历文件
        AdvertisementImageView view = new AdvertisementImageView();
        InitialPreviewConfigView config = new InitialPreviewConfigView();

        for (MultipartFile file : uploadedFiles) {
            if (!file.isEmpty()) {
                String path;
                try {
                    path = ImageUtil.getAdvertisementImageStorePath();
                } catch (ConfigurationException e) {
                    LOG.error("获取文件上传路径失败，文件上传操作终止！");
                    throw new UploadAdvertisementImageFailureException(APIErrorResponse.ErrorCode.E990030.getErrorCode());
                }
                File folder = new File(path);
                if (!folder.exists()) {
                    if (!folder.mkdirs()) {
                        LOG.error("文件夹创建失败： {}", path);
                        throw new UploadAdvertisementImageFailureException(APIErrorResponse.ErrorCode.E990030.getErrorCode());
                    }
                }

                // 进入“上传文件流程”
                String fileName = StringUtils.substringBeforeLast(file.getOriginalFilename(), ".");
                fileName = ImageUtil.replaceFileNameSpecialCharacters(fileName);
                long fileNamePrefix = System.currentTimeMillis();// 时间戳
                int count = RandomUtils.nextInt(6, 15);
                String commonFileNamePrefix = fileNamePrefix + "_" + RandomStringUtils.random(count, 0, 0, true, true, null, new SecureRandom());// 文件名的公共前缀
                String originalFileName = commonFileNamePrefix + "_" + fileName + "_original.jpg";// 生成存储到本地的文件名，生成的是经过压缩的“原始图片”，并且图片类型固定为“jpg”，这个“原始图片”不是用户上传的真正的原始图片
//                String tempOriginalFileName = commonFileNamePrefix + "_" + fileName + "_original_temp.jpg";// “原始图片”的临时文件

                config.setUrl("/administrator/advertisements/images/" + originalFileName);// 用于“删除图片”的URI地址
                view.getInitialPreviewConfigViews().add(config);
                view.setImageName(originalFileName);

//                int newOriginalWidth = ImageUtil.ADVERTISEMENT_IMAGE_WIDTH;// “原始图片”的默认宽度
//                int newOriginalHeight;// “原始图片”的默认高度
                try {
                    // 上传图片
//                    String tempFileName = commonFileNamePrefix + "_" + fileName + "_temp.jpg";// “用户上传的原始图片”的临时文件名
                    File tempUserFile = new File(path + originalFileName);// 将用户上传的文件存储起来，生成临时文件
                    file.transferTo(tempUserFile);// 将用户上传的图片存储到服务器本地
//                    BufferedImage bufferedImage = ImageIO.read(tempUserFile);// 读取图片到BufferedImage

//                    double originalWatermarkSizePercentage = ImageUtil.ORIGINAL_WATERMARK_SIZE_PERCENTAGE;// “原始图片”水印比例
//                    if (bufferedImage.getWidth() < ImageUtil.ADVERTISEMENT_IMAGE_WIDTH) {// 若用户上传的图片的宽度小于系统的“默认宽度”，则使用用户上传的图片的宽度
//                        newOriginalWidth = bufferedImage.getWidth();// 重新(经过压缩)生成的“原始图片”
//
//                        // 重新计算“原始图片”水印比例
//                        originalWatermarkSizePercentage = BigDecimal.valueOf(newOriginalWidth)
//                                .divide(BigDecimal.valueOf(ImageUtil.ADVERTISEMENT_IMAGE_WIDTH), 2, BigDecimal.ROUND_HALF_UP)
//                                .multiply(BigDecimal.valueOf(ImageUtil.ORIGINAL_WATERMARK_SIZE_PERCENTAGE))
//                                .divide(BigDecimal.valueOf(3L), 2, BigDecimal.ROUND_HALF_UP)
//                                .doubleValue();
//                    }
//                    toDeleteFiles.add(tempUserFile);// 添加到“待删除文件列表”中
                    // 上传！
//                    newOriginalHeight = ImageUtil.zoomImageScale(bufferedImage, path + originalFileName, newOriginalWidth);// 重新(经过压缩)生成的“原始图片”
//                    view.setImageWidth(String.valueOf(newOriginalWidth));
//                    view.setImageHeight(String.valueOf(newOriginalHeight));

                    // 最后一步, 将“最终水印”添加到图片中去
                    // “原始图片”添加水印
//                    File tempOriginalFile = new File(path + tempOriginalFileName);
//                    BufferedImage originalImage = ImageIO.read(tempOriginalFile);
//                    BufferedImage outImage = ImageUtil.watermark(originalImage, watermarkImage, ImageUtil.PlacementPosition.BOTTOM_RIGHT, originalWatermarkSizePercentage);
//                    ImageIO.write(outImage, "jpg", new File(path + originalFileName));// 生成最终的“原始图片”
//                    toDeleteFiles.add(tempOriginalFile);// 添加到“待删除文件列表”中
                } catch (IOException e) {
                    LOG.error(e.getMessage(), e);
                    throw new UploadAdvertisementImageFailureException(APIErrorResponse.ErrorCode.E990030.getErrorCode());
                }
                // 构建返回值
                try {
                    url = ImageUtil.getAdvertisementImageUrlPrefix() + originalFileName;
                } catch (ConfigurationException e) {
                    LOG.error(e.getMessage(), e);
                }
            }
        }
        // 删除临时文件
        for (File toDeleteFile : toDeleteFiles) {
            String toDelete = toDeleteFile.getAbsolutePath() + toDeleteFile.getName();
            if (toDeleteFile.delete()) {
                LOG.info("临时文件删除成功： {}", toDelete);
            } else {
                LOG.info("临时文件删除失败：： {}", toDelete);
            }
        }

        String[] previewViews = new String[]{"<img class=\"img-thumbnail\" data-src=\"holder.js/100%x100%\" src=\"" + url + "\">"};// 用于该图片的预览
        view.setInitialPreviewViews(previewViews);

        return view;
    }

    @Override
    public void deleteAdvertisementImage(String fileName) {
        try {
            String path = ImageUtil.getAdvertisementImageStorePath();
            File toDeleteFile = new File(path + fileName);
            if (toDeleteFile.delete()) {
                LOG.info("临时文件删除成功： {}", path + fileName);
            } else {
                LOG.info("临时文件删除失败：： {}", path + fileName);
            }
        } catch (ConfigurationException e) {
            LOG.error("获取文件上传路径失败，文件上传操作终止！");
        }
    }
}
