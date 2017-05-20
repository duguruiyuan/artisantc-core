package cn.artisantc.core.service;

import cn.artisantc.core.exception.CreateOrderFailureException;
import cn.artisantc.core.exception.ItemNotFoundException;
import cn.artisantc.core.exception.ShopNotFoundException;
import cn.artisantc.core.persistence.entity.Administrator;
import cn.artisantc.core.persistence.entity.BaseItemCategory;
import cn.artisantc.core.persistence.entity.BaseOrder;
import cn.artisantc.core.persistence.entity.BasePaymentOrder;
import cn.artisantc.core.persistence.entity.Item;
import cn.artisantc.core.persistence.entity.ItemBidRecord;
import cn.artisantc.core.persistence.entity.ItemBrowseRecord;
import cn.artisantc.core.persistence.entity.ItemDeliveryAddress;
import cn.artisantc.core.persistence.entity.ItemImage;
import cn.artisantc.core.persistence.entity.ItemMarginOrder;
import cn.artisantc.core.persistence.entity.ItemOrder;
import cn.artisantc.core.persistence.entity.ItemOrderDeliveryAddress;
import cn.artisantc.core.persistence.entity.ItemSubCategory;
import cn.artisantc.core.persistence.entity.Merchant;
import cn.artisantc.core.persistence.entity.MerchantMargin;
import cn.artisantc.core.persistence.entity.MerchantMarginAccount;
import cn.artisantc.core.persistence.entity.MyFavoriteItem;
import cn.artisantc.core.persistence.entity.Shop;
import cn.artisantc.core.persistence.entity.User;
import cn.artisantc.core.persistence.helper.ItemMarginOrderHelper;
import cn.artisantc.core.persistence.helper.MarginHelper;
import cn.artisantc.core.persistence.helper.UserHelper;
import cn.artisantc.core.persistence.repository.AdministratorRepository;
import cn.artisantc.core.persistence.repository.ItemBidRecordRepository;
import cn.artisantc.core.persistence.repository.ItemBrowseRecordRepository;
import cn.artisantc.core.persistence.repository.ItemDeliveryAddressRepository;
import cn.artisantc.core.persistence.repository.ItemImageRepository;
import cn.artisantc.core.persistence.repository.ItemMarginOrderRepository;
import cn.artisantc.core.persistence.repository.ItemOrderDeliveryAddressRepository;
import cn.artisantc.core.persistence.repository.ItemOrderRepository;
import cn.artisantc.core.persistence.repository.ItemRepository;
import cn.artisantc.core.persistence.repository.ItemSubCategoryRepository;
import cn.artisantc.core.persistence.repository.MerchantMarginAccountRepository;
import cn.artisantc.core.persistence.repository.MerchantMarginRepository;
import cn.artisantc.core.persistence.repository.MerchantRepository;
import cn.artisantc.core.persistence.repository.MyFavoriteItemRepository;
import cn.artisantc.core.persistence.repository.OAuth2Repository;
import cn.artisantc.core.persistence.repository.ShopRepository;
import cn.artisantc.core.persistence.specification.ItemSpecification;
import cn.artisantc.core.service.payment.PaymentService;
import cn.artisantc.core.util.DateTimeUtil;
import cn.artisantc.core.util.ImageUtil;
import cn.artisantc.core.util.LoginUserUtil;
import cn.artisantc.core.util.PageUtil;
import cn.artisantc.core.util.RandomUtil;
import cn.artisantc.core.util.WordEncoderUtil;
import cn.artisantc.core.web.controller.PaymentConstant;
import cn.artisantc.core.web.rest.v1_0.vo.APIErrorResponse;
import cn.artisantc.core.web.rest.v1_0.vo.ItemBidRecordView;
import cn.artisantc.core.web.rest.v1_0.vo.ItemDetailView;
import cn.artisantc.core.web.rest.v1_0.vo.ItemImageView;
import cn.artisantc.core.web.rest.v1_0.vo.ItemMarginOrderView;
import cn.artisantc.core.web.rest.v1_0.vo.ItemView;
import cn.artisantc.core.web.rest.v1_0.vo.MyItemCountView;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.ItemViewPaginationList;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * “ItemService”接口的实现类。
 * Created by xinjie.li on 2016/9/24.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Service(value = "itemService")
@Transactional
public class ItemServiceImpl implements ItemService {

    private static final Logger LOG = LoggerFactory.getLogger(ItemServiceImpl.class);

    private ItemRepository itemRepository;

    private ConversionService conversionService;

    private ItemSubCategoryRepository subCategoryRepository;

    private ShopRepository shopRepository;

    private OAuth2Repository oAuth2Repository;

    private ItemImageRepository itemImageRepository;

    private MerchantRepository merchantRepository;

    private ItemBidRecordRepository bidRecordRepository;

    private AdministratorRepository administratorRepository;

    private ItemMarginOrderRepository marginOrderRepository;

    private MerchantMarginAccountRepository merchantMarginAccountRepository;

    private MerchantMarginRepository merchantMarginRepository;

    private ItemOrderRepository itemOrderRepository;

    private MessageSource messageSource;

    private PaymentService paymentService;

    private ItemOrderDeliveryAddressRepository itemOrderDeliveryAddressRepository;

    private ItemDeliveryAddressRepository itemDeliveryAddressRepository;

    private ItemBrowseRecordRepository itemBrowseRecordRepository;

    private MyFavoriteItemRepository favoriteItemRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, ConversionService conversionService, ItemSubCategoryRepository subCategoryRepository,
                           ShopRepository shopRepository, OAuth2Repository oAuth2Repository, ItemImageRepository itemImageRepository,
                           MerchantRepository merchantRepository, ItemBidRecordRepository bidRecordRepository, AdministratorRepository administratorRepository,
                           ItemMarginOrderRepository marginOrderRepository, MerchantMarginAccountRepository merchantMarginAccountRepository,
                           MerchantMarginRepository merchantMarginRepository, ItemOrderRepository itemOrderRepository,
                           MessageSource messageSource, PaymentService paymentService, ItemOrderDeliveryAddressRepository itemOrderDeliveryAddressRepository,
                           ItemDeliveryAddressRepository itemDeliveryAddressRepository, ItemBrowseRecordRepository itemBrowseRecordRepository,
                           MyFavoriteItemRepository favoriteItemRepository) {
        this.itemRepository = itemRepository;
        this.conversionService = conversionService;
        this.subCategoryRepository = subCategoryRepository;
        this.shopRepository = shopRepository;
        this.oAuth2Repository = oAuth2Repository;
        this.itemImageRepository = itemImageRepository;
        this.merchantRepository = merchantRepository;
        this.bidRecordRepository = bidRecordRepository;
        this.administratorRepository = administratorRepository;
        this.marginOrderRepository = marginOrderRepository;
        this.merchantMarginAccountRepository = merchantMarginAccountRepository;
        this.merchantMarginRepository = merchantMarginRepository;
        this.itemOrderRepository = itemOrderRepository;
        this.messageSource = messageSource;
        this.paymentService = paymentService;
        this.itemOrderDeliveryAddressRepository = itemOrderDeliveryAddressRepository;
        this.itemDeliveryAddressRepository = itemDeliveryAddressRepository;
        this.itemBrowseRecordRepository = itemBrowseRecordRepository;
        this.favoriteItemRepository = favoriteItemRepository;
    }

    @Override
    public ItemViewPaginationList findByShopSerialNumberAndPage(String shopSerialNumber, int page) {
        if (StringUtils.isBlank(shopSerialNumber)) {
            throw new ShopNotFoundException("没有找到指定“shopSerialNumber”对应的店铺的拍品资源：" + shopSerialNumber);
        }

        // 查询数据
        ItemViewPaginationList paginationList = new ItemViewPaginationList();
        Page<Item> itemPage = this.findPublishedByShopSerialNameAndPage(shopSerialNumber, page);
        List<Item> items = itemPage.getContent();

        paginationList.setTotalPages(String.valueOf(itemPage.getTotalPages()));
        paginationList.setTotalRecords(String.valueOf(itemPage.getTotalElements()));

        // 下一页的页数，这里"+1"的目的是还原当前的页数，而不是查询结果里的页数
        int nextPage = itemPage.getNumber() + 1;

        // 如果还有下一页，则继续"+1"返回下一页的页数
        if (itemPage.hasNext()) {
            nextPage = nextPage + 1;
        }
        LOG.debug("itemPage.getNumber(): {}", itemPage.getNumber());
        LOG.debug("itemPage.getNumberOfElements(): {}", itemPage.getNumberOfElements());
        paginationList.setNextPage(String.valueOf(nextPage));

        // 遍历查询结果列表，并进行类型转换
        if (null != items) {
            paginationList.getItemViews().addAll(this.getItemViewsFromItems(items, Boolean.TRUE));
        }

        return paginationList;
    }

    @Override
    public void releaseItem(String title, String detail, String category, String startDateTime,
                            String endDateTime, String initialPrice, String raisePrice, String freeExpress, String expressFee, String freeReturn,
                            String referencePrice, String fixedPrice, String margin, MultipartFile[] files) {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 校验“我的店铺”是否存在
        Shop shop = shopRepository.findByUser_id(user.getId());
        if (null == shop) {
            throw new ShopNotFoundException("没有找到指定“userId”对应的店铺资源：" + user.getId());
        }

        // 校验“拍品标题”
        if (StringUtils.isBlank(title)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010102.getErrorCode());
        } else {
            if (title.length() > APIErrorResponse.MAX_ITEM_NAME_LENGTH) {
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010115.getErrorCode());
            }
        }

        // 校验“拍品详情”
        if (StringUtils.isNotBlank(detail) && detail.length() > APIErrorResponse.MAX_ITEM_DETAIL_LENGTH) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010116.getErrorCode());
        }

        // 校验“拍品分类”
        ItemSubCategory subCategory;
        if (StringUtils.isBlank(category)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010103.getErrorCode());
        } else {
            subCategory = subCategoryRepository.findByCategoryCode(category);
            if (null == subCategory) {
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010103.getErrorCode());
            }
        }

        // 校验“拍卖开始时间”和“拍卖结束时间”
        Date start, end;
        if (StringUtils.isBlank(startDateTime) || StringUtils.isBlank(endDateTime)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010104.getErrorCode());
        } else {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(DateTimeUtil.DATE_FORMAT_ALL);
                start = sdf.parse(startDateTime);
                end = sdf.parse(endDateTime);
                if (end.compareTo(start) <= 0) {
                    throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010105.getErrorCode());
                }
            } catch (ParseException e) {
                LOG.error(e.getMessage(), e);
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010104.getErrorCode());
            }
        }

        // 校验“起拍价”
        BigDecimal initialPriceBigDecimal;
        if (StringUtils.isBlank(initialPrice)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010106.getErrorCode());
        } else {
            initialPriceBigDecimal = new BigDecimal(initialPrice);
            if (initialPriceBigDecimal.compareTo(BigDecimal.ZERO) < 1) {
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010107.getErrorCode());
            }
        }

        // 校验“加价幅度”
        BigDecimal raisePriceBigDecimal;
        if (StringUtils.isBlank(raisePrice)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010108.getErrorCode());
        } else {
            raisePriceBigDecimal = new BigDecimal(raisePrice);
            if (raisePriceBigDecimal.compareTo(BigDecimal.ZERO) < 1) {
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010109.getErrorCode());
            }
        }

        // 校验“包邮”和“邮费”
        boolean isFreeExpress;
        if (NumberUtils.isParsable(freeExpress)) {
            isFreeExpress = BooleanUtils.toBoolean(NumberUtils.toInt(freeExpress));
        } else {
            isFreeExpress = BooleanUtils.toBoolean(freeExpress);
        }
        BigDecimal expressFeeBigDecimal = BigDecimal.ZERO;// 邮费
        if (!isFreeExpress) {// 不包邮
            if (StringUtils.isBlank(expressFee)) {// 必须设置“邮费”
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010110.getErrorCode());
            } else {
                expressFeeBigDecimal = new BigDecimal(expressFee);
                if (expressFeeBigDecimal.compareTo(BigDecimal.ZERO) < 1) {// “邮费”必须大于0
                    throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010110.getErrorCode());
                }
            }
        }

        // 校验“包退”
        boolean isFreeReturn;// 如果设置了“包退”，则会对后续的退货流程产生影响
        if (NumberUtils.isParsable(freeReturn)) {
            isFreeReturn = BooleanUtils.toBoolean(NumberUtils.toInt(freeReturn));
        } else {
            isFreeReturn = BooleanUtils.toBoolean(freeReturn);
        }

        // 校验“参考价”
        BigDecimal referencePriceBigDecimal = BigDecimal.ZERO;
        if (StringUtils.isNotBlank(referencePrice)) {// 设置了“参考价”
            referencePriceBigDecimal = new BigDecimal(referencePrice);
            if (referencePriceBigDecimal.compareTo(BigDecimal.ZERO) < 1) {// 必须大于0
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010111.getErrorCode());
            }
        }

        // 校验“一口价”
        BigDecimal fixedPriceBigDecimal = BigDecimal.ZERO;
        if (StringUtils.isNotBlank(fixedPrice)) {// 设置了“一口价”
            fixedPriceBigDecimal = new BigDecimal(fixedPrice);
            // 如果设置了“一口价”，即“一口价”大于0了，则必须大于“起拍价
            if (fixedPriceBigDecimal.compareTo(BigDecimal.ZERO) == 1 && fixedPriceBigDecimal.compareTo(initialPriceBigDecimal) < 1) {
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010112.getErrorCode());
            }
        }

        // 校验“保证金”
        BigDecimal marginBigDecimal = BigDecimal.ZERO;
        if (StringUtils.isNotBlank(margin)) {
            marginBigDecimal = new BigDecimal(margin);
            if (marginBigDecimal.compareTo(BigDecimal.ZERO) == 1) {// 设置了“保证金”
                if (marginBigDecimal.compareTo(BigDecimal.ZERO) == 1) {
                    // 查找“商家保证金账户”
                    MerchantMarginAccount merchantMarginAccount = merchantMarginAccountRepository.findByUser_id(user.getId());
                    if (null != merchantMarginAccount && merchantMarginAccount.getAmount().compareTo(BigDecimal.ZERO) > 0) {
                        // 查找系统预先定义的“MerchantMargin”
                        List<MerchantMargin> merchantMargins = merchantMarginRepository.findAll();
                        assert merchantMargins != null && !merchantMargins.isEmpty();// 这个应该是系统上线的初始化数据，不应该为空！

                        boolean availableMargin = Boolean.FALSE;
                        for (MerchantMargin merchantMargin : merchantMargins) {
                            if (merchantMarginAccount.getAmount().compareTo(merchantMargin.getMerchantMargin()) >= 0) {
                                // 商家可以使用的“保证金场”
                                if (marginBigDecimal.compareTo(merchantMargin.getUserMargin()) == 0) {
                                    // 商家设置的“保证金”是有效的值
                                    availableMargin = Boolean.TRUE;
                                    break;
                                }
                            }
                        }

                        if (!availableMargin) {
                            // 商家设置的“保证金”是无效的值
                            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010132.getErrorCode());
                        }
                    } else {
                        // 商家设置的“保证金”是无效的值
                        throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010132.getErrorCode());
                    }
                }
            }
        }

        // 校验“拍品图片”
        List<MultipartFile> uploadedFiles = new ArrayList<>();// 真正提交过来准备上传的图片
        if (null == files) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010114.getErrorCode());
        } else {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    if (!ImageUtil.isPicture(file)) {
                        throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E990031.getErrorCode());
                    }
                    uploadedFiles.add(file);
                }
            }
            if (uploadedFiles.size() < APIErrorResponse.MIN_ITEM_IMAGE_LENGTH) {
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010114.getErrorCode());
            }
            if (uploadedFiles.size() > APIErrorResponse.MAX_ITEM_IMAGE_LENGTH) {
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010117.getErrorCode());
            }
        }

        // TODO: 2016/10/6 校验当前用户的类型，根据不同类型决定是否可以发布拍品：实名认证的每天3天，个人商家的每天5条，企业商家不限制。

        // 构建“拍品”数据
        Item item = new Item();

        Date date = new Date();
        item.setCreateDateTime(date);
        item.setUpdateDateTime(date);
        item.setCategory(BaseItemCategory.SubCategory.valueOf(subCategory.getCategoryCode()));
        item.setDetail(detail);
        item.setEndDateTime(end);
        item.setStartDateTime(start);

        item.setFixedPrice(fixedPriceBigDecimal);
        item.setFreeExpress(isFreeExpress);
        if (!isFreeExpress) {
            item.setExpressFee(expressFeeBigDecimal);
        }
        item.setFreeReturn(isFreeReturn);
        item.setInitialPrice(initialPriceBigDecimal);
        item.setMargin(marginBigDecimal);
        item.setRaisePrice(raisePriceBigDecimal);
        item.setReferencePrice(referencePriceBigDecimal);
        item.setTitle(title);
        item.setStatus(Item.Status.PENDING_REVIEW);

        item.setShop(shop);

        // 处理“拍品图片”的上传
        // 进入“上传文件流程”
        List<File> toDeleteFiles = new ArrayList<>();// “待删除文件列表”，用于记录过程中产生的临时文件，在完成后删除掉
        long fileNamePrefix = System.currentTimeMillis();// 时间戳
        String commonFileNamePrefix = fileNamePrefix + "_" + user.getSerialNumber();// 文件名的公共前缀
        String path;// 图片的存储路径
        Merchant merchant = merchantRepository.findByUser_id(user.getId());
        try {
            String shopAvatarPath = ImageUtil.getMerchantStorePath(DateFormatUtils.format(merchant.getCreateDateTime(), DateTimeUtil.DATE_FORMAT_ALL), merchant.getRealName(), merchant.getIdentityNumber(), merchant.getUser().getId(), merchant.getUser().getSerialNumber()) + File.separatorChar + "shop" + File.separatorChar;
            path = shopAvatarPath + "items" + File.separatorChar;
        } catch (ConfigurationException e) {
            LOG.error("获取文件上传路径失败，文件上传操作终止！");
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E990030.getErrorCode());
        }
        File folder = new File(path);// 店铺头像的存储文件夹
        if (!folder.exists()) {
            if (!folder.mkdirs()) {
                LOG.error("文件夹创建失败： {}", path);
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E990030.getErrorCode());
            }
        }

        // 准备水印图片
        BufferedImage finalWatermarkImage;
        try {
            // 第1步， 生成“昵称水印”
            BufferedImage image1 = ImageUtil.convertTextToGraphic("@ " + user.getProfile().getNickname());
            File watermark1 = new File(path + commonFileNamePrefix + "_watermark_1.png");
            ImageIO.write(image1, "png", watermark1);
            toDeleteFiles.add(watermark1);// 添加到“待删除文件列表”中

            // 第2步 生成“匠号水印”
            BufferedImage image2 = ImageUtil.convertTextToGraphic(user.getSerialNumber());
            File watermark2 = new File(path + commonFileNamePrefix + "_watermark_2.png");
            ImageIO.write(image2, "png", watermark2);
            toDeleteFiles.add(watermark2);// 添加到“待删除文件列表”中

            // 第3步， 以“纵向”组合的方式将“昵称水印”和“匠号水印”组合成用户独有的“用户水印”
            BufferedImage joinedImage = ImageUtil.joinBufferedImage(image1, image2, ImageUtil.Orientation.VERTICAL);
            File watermark3 = new File(path + commonFileNamePrefix + "_watermark_3.png");
            ImageIO.write(joinedImage, "png", watermark3);
            toDeleteFiles.add(watermark3);// 添加到“待删除文件列表”中

            // 第4步， 以“横向”组合的方式将“用户水印”和“LOGO水印”组合“最终水印”
            BufferedImage watermarkImage = ImageIO.read(new File(ImageUtil.getWatermarkStorePath()));
            finalWatermarkImage = ImageUtil.joinBufferedImage(watermarkImage, joinedImage, ImageUtil.Orientation.HORIZONTAL);
            File watermarkFinal = new File(path + commonFileNamePrefix + "_watermark_final.png");
            ImageIO.write(finalWatermarkImage, "png", watermarkFinal);
            toDeleteFiles.add(watermarkFinal);// 添加到“待删除文件列表”中
        } catch (IOException | ConfigurationException e) {
            LOG.error(e.getMessage(), e);
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E990030.getErrorCode());
        }
        // 上传图片
        List<ItemImage> itemImages = new ArrayList<>();
        for (MultipartFile file : uploadedFiles) {
            if (!file.isEmpty()) {
                String fileName = StringUtils.substringBeforeLast(file.getOriginalFilename(), ".");
                fileName = ImageUtil.replaceFileNameSpecialCharacters(fileName);
                int originalImageHeight, thumbnailImageHeight;// 图片的高度

                String originalFileName = commonFileNamePrefix + "_" + fileName + "_original.jpg";// 生成存储到本地的文件名，生成的是经过压缩的“原始图片”，并且图片类型固定为“jpg”，这个“原始图片”不是用户上传的真正的原始图片
                String thumbnailFileName = commonFileNamePrefix + "_" + fileName + "_thumbnail.jpg";// 相对于“原始图片”的“缩略图”

                String tempOriginalFileName = commonFileNamePrefix + "_" + fileName + "_original_temp.jpg";// “原始图片”的临时文件
                String tempThumbnailFileName = commonFileNamePrefix + "_" + fileName + "_thumbnail_temp.jpg";// 相对于“原始图片”的“缩略图”的临时文件

                int newOriginalWidth = ImageUtil.ITEM_IMAGE_WIDTH;// “原始图片”的默认宽度
                int newThumbnailWidth = ImageUtil.ITEM_IMAGE_THUMBNAIL_WIDTH;// “缩略图片”的默认宽度
                try {
                    // 上传图片
                    String tempFileName = commonFileNamePrefix + "_" + fileName + "_temp.jpg";// “用户上传的原始图片”的临时文件名
                    File tempUserFile = new File(path + tempFileName);// 将用户上传的文件存储起来，生成临时文件
                    file.transferTo(tempUserFile);// 将用户上传的图片存储到服务器本地
                    BufferedImage bufferedImage = ImageIO.read(tempUserFile);// 读取图片到BufferedImage

                    double originalWatermarkSizePercentage = ImageUtil.ORIGINAL_WATERMARK_SIZE_PERCENTAGE;// “原始图片”水印比例
                    double thumbnailWatermarkSizePercentage = ImageUtil.THUMBNAIL_WATERMARK_SIZE_PERCENTAGE;// “缩略图片”水印比例
                    if (bufferedImage.getWidth() < ImageUtil.ITEM_IMAGE_WIDTH) {// 若用户上传的图片的宽度小于系统的“默认宽度”，则使用用户上传的图片的宽度
                        newOriginalWidth = bufferedImage.getWidth();// 重新(经过压缩)生成的“原始图片”
                        newThumbnailWidth = bufferedImage.getWidth() / 2;// 相对于“原始图片”的“缩略图”

                        // 重新计算“原始图片”水印比例
                        originalWatermarkSizePercentage = BigDecimal.valueOf(newOriginalWidth)
                                .divide(BigDecimal.valueOf(ImageUtil.ITEM_IMAGE_WIDTH), 2, BigDecimal.ROUND_HALF_UP)
                                .multiply(BigDecimal.valueOf(ImageUtil.ORIGINAL_WATERMARK_SIZE_PERCENTAGE))
                                .divide(BigDecimal.valueOf(3L), 2, BigDecimal.ROUND_HALF_UP)
                                .doubleValue();
                        // 重新计算“缩略图片”水印比例
                        thumbnailWatermarkSizePercentage = BigDecimal.valueOf(newThumbnailWidth)
                                .divide(BigDecimal.valueOf(ImageUtil.ITEM_IMAGE_THUMBNAIL_WIDTH), 2, BigDecimal.ROUND_HALF_UP)
                                .multiply(BigDecimal.valueOf(ImageUtil.THUMBNAIL_WATERMARK_SIZE_PERCENTAGE))
                                .divide(BigDecimal.valueOf(3L), 2, BigDecimal.ROUND_HALF_UP)
                                .doubleValue();
                    }
                    toDeleteFiles.add(tempUserFile);// 添加到“待删除文件列表”中
                    // 上传！
                    originalImageHeight = ImageUtil.zoomImageScale(bufferedImage, path + tempOriginalFileName, newOriginalWidth);// 重新(经过压缩)生成的“原始图片”
                    thumbnailImageHeight = ImageUtil.zoomImageScale(bufferedImage, path + tempThumbnailFileName, newThumbnailWidth);// 相对于“原始图片”的“缩略图”

                    // 最后一步, 将“最终水印”添加到图片中去
                    // “原始图片”添加水印
                    File tempOriginalFile = new File(path + tempOriginalFileName);
                    BufferedImage originalImage = ImageIO.read(tempOriginalFile);
                    BufferedImage outImage = ImageUtil.watermark(originalImage, finalWatermarkImage, ImageUtil.PlacementPosition.BOTTOM_RIGHT, originalWatermarkSizePercentage);
                    ImageIO.write(outImage, "jpg", new File(path + originalFileName));// 生成最终的“原始图片”
                    toDeleteFiles.add(tempOriginalFile);// 添加到“待删除文件列表”中

                    // “缩略图片”添加水印
                    File tempThumbnailFile = new File(path + tempThumbnailFileName);
                    BufferedImage thumbnailImage = ImageIO.read(tempThumbnailFile);
                    BufferedImage thumbnailOutImage = ImageUtil.watermark(thumbnailImage, finalWatermarkImage, ImageUtil.PlacementPosition.BOTTOM_RIGHT, thumbnailWatermarkSizePercentage);
                    ImageIO.write(thumbnailOutImage, "jpg", new File(path + thumbnailFileName));// 生成最终的“缩略图片”
                    toDeleteFiles.add(tempThumbnailFile);// 添加到“待删除文件列表”中
                } catch (IOException e) {
                    LOG.error(e.getMessage(), e);
                    throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E990030.getErrorCode());
                }

                ItemImage itemImage = new ItemImage();
                itemImage.setCreateDateTime(date);
                itemImage.setUpdateDateTime(date);
                itemImage.setItem(item);
                itemImage.setImageName(originalFileName);
                itemImage.setImageWidth(newOriginalWidth);
                itemImage.setImageHeight(originalImageHeight);
                itemImage.setThumbnailName(thumbnailFileName);
                itemImage.setThumbnailWidth(newThumbnailWidth);
                itemImage.setThumbnailHeight(thumbnailImageHeight);

                itemImage = itemImageRepository.save(itemImage);
                itemImages.add(itemImage);
            }
        }
        // 删除临时文件
        for (File toDeleteFile : toDeleteFiles) {
            String toDelete = toDeleteFile.getAbsolutePath() + toDeleteFile.getName();
            if (toDeleteFile.delete()) {
                LOG.info("临时文件删除成功： {})", toDelete);
            } else {
                LOG.info("临时文件删除失败：： {})", toDelete);
            }
        }
        item.setImages(itemImages);

        itemRepository.save(item);
    }

    @Override
    public ItemDetailView findById(String itemId) {
        // 校验“拍品ID”
        Item item = this.checkItemId(itemId);

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }
        // 如果该拍品处于“待审核状态”，则只允许其发布者查看
        if (user.getId() != item.getShop().getUser().getId() && item.getStatus() == Item.Status.PENDING_REVIEW) {
            throw new ItemNotFoundException("当前登录用户没有查看“itemId”拍品的权限：" + itemId);
        }

        return this.getItemDetailView(item, user);
    }

    @Override
    public ItemDetailView findPendingReviewItemById(String itemId) {
        // 校验“拍品ID”
        Item item = this.checkItemId(itemId);

        // 校验当前管理员用户信息
        Administrator administrator = administratorRepository.findByUsername(LoginUserUtil.getLoginUsername());
        if (null == administrator) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        return this.getItemDetailView(item, null);
    }

    @Override
    public void bid(String itemId, String bidPrice) {
        // 校验“拍品ID”
        Item item = this.checkItemId(itemId);

        if (Item.Status.BIDDING != item.getStatus()) {
            // 判断拍品的状态处于“是否可以出价竞拍”，若不可以则抛出异常
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010125.getErrorCode());
        }

        // 校验“竞拍价格”
        if (StringUtils.isBlank(bidPrice)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010120.getErrorCode());
        } else {
            BigDecimal bidPriceBigDecimal;
            try {
                bidPriceBigDecimal = new BigDecimal(bidPrice);
                if (bidPriceBigDecimal.compareTo(BigDecimal.ZERO) == -1) {
                    throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010120.getErrorCode());
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010120.getErrorCode());
            }

            if (bidPriceBigDecimal.compareTo(item.getInitialPrice()) == -1) {
                // 出价必须高于拍品的“起拍价”
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010124.getErrorCode());
            }
            this.bidItem(item, bidPriceBigDecimal);
        }
    }

    @Override
    public String fixed(String itemId) {
        // 校验“拍品ID”
        Item item = this.checkItemId(itemId);

        if (Item.Status.BIDDING != item.getStatus()) {
            // 判断拍品的状态处于“是否可以出价竞拍”，若不可以则抛出异常
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010125.getErrorCode());
        }

        ItemOrder itemOrder = this.bidItem(item, item.getFixedPrice());
        if (null == itemOrder) {
            return "";
        }
        return itemOrder.getOrderNumber();
    }

    @Override
    public ItemViewPaginationList findPendingReviewItems(int page) {
        return this.getItemViewsBySpecification(ItemSpecification.findAllPendingReview(), Boolean.FALSE, page);
    }

    @Override
    public ItemViewPaginationList findMyItemsByPage(int page) {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        return this.getItemViewsBySpecification(ItemSpecification.findMyItems(user.getId()), Boolean.TRUE, page);
    }

    @Override
    public void approve(String itemId) {
        // 校验“拍品ID”
        Item item = this.checkItemId(itemId);

        // 审核通过
        if (Item.Status.PENDING_REVIEW == item.getStatus()) {
            item.setStatus(Item.Status.PREVIEW);
            item.setUpdateDateTime(new Date());

            item = itemRepository.save(item);

            this.checkItemIsOverTime(item);
        }
    }

    @Override
    public void reject(String itemId) {
        // 校验“拍品ID”
        Item item = this.checkItemId(itemId);

        // 审核不通过
        if (Item.Status.PENDING_REVIEW == item.getStatus()) {
            item.setStatus(Item.Status.REJECTED);
            item.setUpdateDateTime(new Date());

            itemRepository.save(item);
        }
    }

    @Override
    public String createMarginOrder(String itemId, String paymentChannel) {
        // 校验“拍品ID”
        Item item = this.checkItemId(itemId);

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }
        if (user.getId() == item.getShop().getUser().getId()) {
            // 不可以对自己发布的拍品缴纳保证金
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010128.getErrorCode());
        }

        // 校验“支付渠道”
        if (StringUtils.isBlank(paymentChannel)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010202.getErrorCode());
        }
        BaseOrder.PaymentChannel channel = null;
        for (BaseOrder.PaymentChannel payment : BaseOrder.PaymentChannel.values()) {
            if (payment.name().equals(paymentChannel)) {
                channel = payment;
            }
        }
        if (null == channel) {
            // 说明指定的“支付渠道”不在系统支持的列表内
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010203.getErrorCode());
        }

        // 当且仅当：
        // 1、拍品仍处于“竞拍中”状态
        // 2、并且该拍品需要“缴纳保证金”时才允许可以缴纳保证金
        if (Item.Status.BIDDING == item.getStatus()
                && item.getMargin().compareTo(BigDecimal.ZERO) > 0) {
            // 查找是否已经生成过支付订单
            ItemMarginOrder order = marginOrderRepository.findByItem_idAndUser_idAndStatus(item.getId(), user.getId(), ItemMarginOrder.Status.PENDING_PAY);
            if (null == order) {
                // 没有“待支付”的保证金订单，则生成订单
                try {
                    order = ItemMarginOrderHelper.createPaymentOrder(user, item, channel);
                    order = marginOrderRepository.save(order);
                } catch (Exception e) {
                    LOG.error(e.getMessage(), e);
                    throw new CreateOrderFailureException();
                }
            }
            String subject = MarginHelper.MARGIN_TITLE;
            return paymentService.getPaySign(order.getOrderNumber(), order.getAmount(), channel, PaymentConstant.TIMEOUT_MARGIN_ORDER, subject);
        } else {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010126.getErrorCode());
        }
    }

    @Override
    public String showBidByItemId(String itemId) {
        // 校验“拍品ID”
        Item item = this.checkItemId(itemId);

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        final String marginShow = "MARGIN";// 显示“缴纳保证金”

        if (item.getMargin().compareTo(BigDecimal.ZERO) == 1) {
            // 该拍品需要缴纳保证金，校验用户是否缴纳
            ItemMarginOrder marginOrder = marginOrderRepository.findByItem_idAndUser_idAndStatus(item.getId(), user.getId(), ItemMarginOrder.Status.PAID);
            if (null != marginOrder) {
                // 判断拍品是否允许一口价
                return this.isFixedPrice(item.getFixedPrice());
            } else {
                // 尝试查找“待付款”的订单信息
                marginOrder = marginOrderRepository.findByItem_idAndUser_idAndStatus(item.getId(), user.getId(), ItemMarginOrder.Status.PENDING_PAY);
                if (null != marginOrder) {
                    // 同步“支付渠道”的订单信息
                    if (null != marginOrder.getPaymentChannel() && BaseOrder.PaymentChannel.A_LI_PAY == marginOrder.getPaymentChannel()) {
                        marginOrder = paymentService.getALiPayOrder(marginOrder);// 同步“支付宝”的订单信息
                    } else if (null != marginOrder.getPaymentChannel() && BaseOrder.PaymentChannel.WEIXIN_PAY == marginOrder.getPaymentChannel()) {
                        marginOrder = paymentService.getWeiXinPayOrder(marginOrder);// 同步“微信支付”的订单信息
                    }

                    if (ItemMarginOrder.Status.PAID == marginOrder.getStatus()) {
                        // 判断拍品是否允许一口价
                        return this.isFixedPrice(item.getFixedPrice());
                    } else {
                        return marginShow;// 未缴纳保证金
                    }
                } else {
                    return marginShow;// 未缴纳保证金
                }
            }
        } else {// 该拍品不需要缴纳保证金
            // 判断拍品是否允许一口价
            return this.isFixedPrice(item.getFixedPrice());
        }
    }

    @Override
    public ItemViewPaginationList findPendingReviewByShopSerialNumberAndPage(String shopSerialNumber, int page) {
        List<Item.Status> statuses = new ArrayList<>();
        statuses.add(Item.Status.PENDING_REVIEW);
        return this.getItemViewsBySpecification(ItemSpecification.findAllByShopSerialNumberAndStatus(shopSerialNumber, statuses), Boolean.FALSE, page);
    }

    @Override
    public ItemViewPaginationList findPreviewByShopSerialNumberAndPage(String shopSerialNumber, int page) {
        List<Item.Status> statuses = new ArrayList<>();
        statuses.add(Item.Status.PREVIEW);
        return this.getItemViewsBySpecification(ItemSpecification.findAllByShopSerialNumberAndStatus(shopSerialNumber, statuses), Boolean.FALSE, page);
    }

    @Override
    public ItemViewPaginationList findBiddingByShopSerialNumberAndPage(String shopSerialNumber, int page) {
        List<Item.Status> statuses = new ArrayList<>();
        statuses.add(Item.Status.BIDDING);
        return this.getItemViewsBySpecification(ItemSpecification.findAllByShopSerialNumberAndStatus(shopSerialNumber, statuses), Boolean.FALSE, page);
    }

    @Override
    public ItemViewPaginationList findEndByShopSerialNumberAndPage(String shopSerialNumber, int page) {
        List<Item.Status> statuses = new ArrayList<>();
        statuses.add(Item.Status.END);
        statuses.add(Item.Status.REJECTED);
        return this.getItemViewsBySpecification(ItemSpecification.findAllByShopSerialNumberAndStatus(shopSerialNumber, statuses), Boolean.FALSE, page);
    }

    @Override
    public ItemViewPaginationList findMyBiddingItems(int page) {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 获取“我的出价并且拍品仍处在竞拍状态中”的记录
        PageUtil pageUtil = new PageUtil(PageUtil.SHOP_ITEM_PAGE_SIZE);// 设置每页的记录数
        page = pageUtil.getPageForPageable(page);

        Pageable pageable = new PageRequest(page, pageUtil.getPageSize());
        Page<ItemBidRecord> itemBidRecordPage = bidRecordRepository.findByUser_idAndItem_statusOrderByItem_idDesc(user.getId(), Item.Status.BIDDING, pageable);

        // 构建返回数据
        ItemViewPaginationList paginationList = new ItemViewPaginationList();
        paginationList.setTotalPages(String.valueOf(itemBidRecordPage.getTotalPages()));
        paginationList.setTotalRecords(String.valueOf(itemBidRecordPage.getTotalElements()));

        // 下一页的页数，这里"+1"的目的是还原当前的页数，而不是查询结果里的页数
        int nextPage = itemBidRecordPage.getNumber() + 1;

        // 如果还有下一页，则继续"+1"返回下一页的页数
        if (itemBidRecordPage.hasNext()) {
            nextPage = nextPage + 1;
        }
        LOG.debug("itemBidRecordPage.getNumber(): {}", itemBidRecordPage.getNumber());
        LOG.debug("itemBidRecordPage.getNumberOfElements(): {}", itemBidRecordPage.getNumberOfElements());
        paginationList.setNextPage(String.valueOf(nextPage));

        List<ItemBidRecord> itemBidRecords = itemBidRecordPage.getContent();
        if (null != itemBidRecords) {
            List<ItemView> itemViews = new ArrayList<>();
            for (ItemBidRecord itemBidRecord : itemBidRecords) {
                itemViews.add(this.getItemViewFromItem(itemBidRecord.getItem(), Boolean.TRUE));
            }
            paginationList.getItemViews().addAll(itemViews);
        }
        return paginationList;
    }

    @Override
    public ItemViewPaginationList findMyWinBidItems(int page) {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 获取“我中标的拍品”的记录
        PageUtil pageUtil = new PageUtil(PageUtil.SHOP_ITEM_PAGE_SIZE);// 设置每页的记录数
        page = pageUtil.getPageForPageable(page);

        Pageable pageable = new PageRequest(page, pageUtil.getPageSize());
        Page<ItemOrder> itemOrderPage = itemOrderRepository.findByUser_idOrderByItem_idDesc(user.getId(), pageable);

        // 构建返回数据
        ItemViewPaginationList paginationList = new ItemViewPaginationList();
        paginationList.setTotalPages(String.valueOf(itemOrderPage.getTotalPages()));
        paginationList.setTotalRecords(String.valueOf(itemOrderPage.getTotalElements()));

        // 下一页的页数，这里"+1"的目的是还原当前的页数，而不是查询结果里的页数
        int nextPage = itemOrderPage.getNumber() + 1;

        // 如果还有下一页，则继续"+1"返回下一页的页数
        if (itemOrderPage.hasNext()) {
            nextPage = nextPage + 1;
        }
        LOG.debug("itemOrderPage.getNumber(): {}", itemOrderPage.getNumber());
        LOG.debug("itemOrderPage.getNumberOfElements(): {}", itemOrderPage.getNumberOfElements());
        paginationList.setNextPage(String.valueOf(nextPage));

        List<ItemOrder> itemOrders = itemOrderPage.getContent();
        if (null != itemOrders) {
            List<ItemView> itemViews = new ArrayList<>();
            for (ItemOrder itemOrder : itemOrders) {
                ItemView view = conversionService.convert(itemOrder.getItem(), ItemView.class);// 类型转换
                view.setMaxBidPrice(String.valueOf(itemOrder.getAmount()));// 订单金额就是拍品的最高出价，因为是成交价格

                itemViews.add(view);
            }
            paginationList.getItemViews().addAll(itemViews);
        }
        return paginationList;
    }

    @Override
    public ItemViewPaginationList findMyFailedBidItems(int page) {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 获取“我的出价并且拍品仍处在竞拍状态中”的记录
        PageUtil pageUtil = new PageUtil(PageUtil.SHOP_ITEM_PAGE_SIZE);// 设置每页的记录数
        page = pageUtil.getPageForPageable(page);

        Pageable pageable = new PageRequest(page, pageUtil.getPageSize());
        Page<ItemBidRecord> itemBidRecordPage = bidRecordRepository.findByUser_idAndItem_statusOrderByItem_idDesc(user.getId(), Item.Status.END, pageable);

        // 构建返回数据
        ItemViewPaginationList paginationList = new ItemViewPaginationList();
        paginationList.setTotalPages(String.valueOf(itemBidRecordPage.getTotalPages()));
        paginationList.setTotalRecords(String.valueOf(itemBidRecordPage.getTotalElements()));

        // 下一页的页数，这里"+1"的目的是还原当前的页数，而不是查询结果里的页数
        int nextPage = itemBidRecordPage.getNumber() + 1;

        // 如果还有下一页，则继续"+1"返回下一页的页数
        if (itemBidRecordPage.hasNext()) {
            nextPage = nextPage + 1;
        }
        LOG.debug("itemBidRecordPage.getNumber(): {}", itemBidRecordPage.getNumber());
        LOG.debug("itemBidRecordPage.getNumberOfElements(): {}", itemBidRecordPage.getNumberOfElements());
        paginationList.setNextPage(String.valueOf(nextPage));

        List<ItemBidRecord> itemBidRecords = itemBidRecordPage.getContent();
        if (null != itemBidRecords) {
            List<ItemView> itemViews = new ArrayList<>();
            for (ItemBidRecord itemBidRecord : itemBidRecords) {
                // 拍品的当前最高出价记录
                ItemBidRecord bidRecord = bidRecordRepository.findFirstByItem_idOrderByBidPriceDesc(itemBidRecord.getItem().getId());

                // 当“已结束”的拍品的“最高出价人”不是当前登录用户时，说明该拍品是当前登录用户“参与竞拍但未中标”的拍品，对当前登录用户来说就是他的“已结束”拍品
                if (user.getId() != bidRecord.getUser().getId()) {
                    ItemView view = conversionService.convert(itemBidRecord.getItem(), ItemView.class);// 类型转换
                    view.setMaxBidPrice(String.valueOf(bidRecord.getBidPrice()));// 当前最高价

                    itemViews.add(view);
                }
            }
            paginationList.getItemViews().addAll(itemViews);
        }
        return paginationList;
    }

    @Override
    public ItemView getItemViewFromItem(Item item, boolean isNeedBidRecord) {
        ItemView view = new ItemView();
        if (null != item) {
            this.checkItemIsOverTime(item);
            view = conversionService.convert(item, ItemView.class);// 类型转换

            if (isNeedBidRecord) {// 如果需要“出价竞拍”
                ItemBidRecord bidRecord = bidRecordRepository.findFirstByItem_idOrderByBidPriceDesc(item.getId());
                if (null != bidRecord) {
                    view.setMaxBidPrice(String.valueOf(bidRecord.getBidPrice()));// 当前最高价
                }
            }
        }
        return view;
    }

    @Override
    public MyItemCountView findMyItemCount() {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        MyItemCountView view = new MyItemCountView();
        view.setBiddingItemCount(String.valueOf(bidRecordRepository.countByUser_idAndItem_status(user.getId(), Item.Status.BIDDING)));// “我参与的竞拍中”的拍品数量

        long endBidItemCount = bidRecordRepository.countByUser_idAndItem_status(user.getId(), Item.Status.END);// “我参与的已结束”的拍品数量
        long winBidItemCount = itemOrderRepository.countByUser_idOrderByItem_idDesc(user.getId());// “我参与的已拍下”的拍品数量
        long failedBidItemCount = endBidItemCount - winBidItemCount;// “我参与的但未中标”的拍品数量
        if (failedBidItemCount < 0) {
            failedBidItemCount = 0;
        }
        view.setFailedBidItemCount(String.valueOf(failedBidItemCount));
        view.setWinBidItemCount(String.valueOf(winBidItemCount));
        view.setNickname(user.getProfile().getNickname());
        view.setSerialNumber(user.getSerialNumber());
        view.setAvatarFileUrl(UserHelper.getAvatar3xUrl(user));

        return view;
    }

    @Override
    public ItemViewPaginationList findBiddingItems(int page) {
        return this.getItemViewPaginationListByStatus(Item.Status.BIDDING, page);
    }

    @Override
    public ItemViewPaginationList findUpcomingBiddingItems(int page) {
        // 获取时间范围
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startDateTime = calendar.getTime();// 今天的开始时间
        LOG.debug("startDateTime: {}", DateFormatUtils.format(startDateTime, DateTimeUtil.DATE_FORMAT_ALL));

        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date endDateTime = calendar.getTime();// 今天的结束时间
        LOG.debug("endDateTime: {}", DateFormatUtils.format(endDateTime, DateTimeUtil.DATE_FORMAT_ALL));

        // 查询数据：找到“拍品”中，处于“Item.Status.PREVIEW”的拍品
        PageUtil pageUtil = new PageUtil(PageUtil.SHOP_ITEM_PAGE_SIZE);// 设置每页的记录数
        page = pageUtil.getPageForPageable(page);

        Pageable pageable = new PageRequest(page, pageUtil.getPageSize());
        Page<Item> itemPage = itemRepository.findByStatusAndStartDateTimeBetween(Item.Status.PREVIEW, startDateTime, endDateTime, pageable);
        List<Item> items = itemPage.getContent();

        ItemViewPaginationList paginationList = new ItemViewPaginationList();
        paginationList.setTotalPages(String.valueOf(itemPage.getTotalPages()));
        paginationList.setTotalRecords(String.valueOf(itemPage.getTotalElements()));

        // 下一页的页数，这里"+1"的目的是还原当前的页数，而不是查询结果里的页数
        int nextPage = itemPage.getNumber() + 1;

        // 如果还有下一页，则继续"+1"返回下一页的页数
        if (itemPage.hasNext()) {
            nextPage = nextPage + 1;
        }
        LOG.debug("itemPage.getNumber(): {}", itemPage.getNumber());
        LOG.debug("itemPage.getNumberOfElements(): {}", itemPage.getNumberOfElements());
        paginationList.setNextPage(String.valueOf(nextPage));
        if (null != items) {
            paginationList.getItemViews().addAll(this.getItemViewsFromItems(items, Boolean.FALSE));
        }
        return paginationList;
    }

    @Override
    public ItemViewPaginationList findPreviewItems(int page) {
        // 获取时间范围
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 1);// “今天”+1天，就是“明天”
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startDateTime = calendar.getTime();// “明天”的开始时间
        LOG.debug("startDateTime: {}", DateFormatUtils.format(startDateTime, DateTimeUtil.DATE_FORMAT_ALL));

        // 查询数据：找到“拍品”中，处于“Item.Status.PREVIEW”的拍品
        PageUtil pageUtil = new PageUtil(PageUtil.SHOP_ITEM_PAGE_SIZE);// 设置每页的记录数
        page = pageUtil.getPageForPageable(page);

        Pageable pageable = new PageRequest(page, pageUtil.getPageSize());
        Page<Item> itemPage = itemRepository.findByStatusAndStartDateTimeAfter(Item.Status.PREVIEW, startDateTime, pageable);
        List<Item> items = itemPage.getContent();

        ItemViewPaginationList paginationList = new ItemViewPaginationList();
        paginationList.setTotalPages(String.valueOf(itemPage.getTotalPages()));
        paginationList.setTotalRecords(String.valueOf(itemPage.getTotalElements()));

        // 下一页的页数，这里"+1"的目的是还原当前的页数，而不是查询结果里的页数
        int nextPage = itemPage.getNumber() + 1;

        // 如果还有下一页，则继续"+1"返回下一页的页数
        if (itemPage.hasNext()) {
            nextPage = nextPage + 1;
        }
        LOG.debug("itemPage.getNumber(): {}", itemPage.getNumber());
        LOG.debug("itemPage.getNumberOfElements(): {}", itemPage.getNumberOfElements());
        paginationList.setNextPage(String.valueOf(nextPage));
        if (null != items) {
            paginationList.getItemViews().addAll(this.getItemViewsFromItems(items, Boolean.FALSE));
        }
        return paginationList;
    }

    @Override
    public ItemMarginOrderView findItemMarginOrderByItemId(String itemId, String paymentOrderNumber) {
        // 校验“拍品ID”
        Item item = this.checkItemId(itemId);

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        ItemMarginOrder marginOrder = marginOrderRepository.findByItem_idAndUser_idAndStatus(item.getId(), user.getId(), ItemMarginOrder.Status.PAID);
        if (null == marginOrder) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010147.getErrorCode());
        }

        // 校验“支付订单”是否超时，如果超时则将该订单设置为“付款超时”
        if (BasePaymentOrder.Status.TIMEOUT_PAY != marginOrder.getStatus()
                && DateTimeUtil.isPaymentOrderOvertime(marginOrder.getCreateDateTime(), PaymentConstant.TIMEOUT_MARGIN_ORDER)) {
            marginOrder.setUpdateDateTime(new Date());
            marginOrder.setStatus(BasePaymentOrder.Status.TIMEOUT_PAY);
            marginOrderRepository.save(marginOrder);

            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010209.getErrorCode());
        }

        // 保存“支付渠道”的订单号
        if (StringUtils.isNotBlank(paymentOrderNumber)) {
            marginOrder.setPaymentOrderNumber(paymentOrderNumber);
            marginOrder = marginOrderRepository.save(marginOrder);
        }

        // 需要去找支付渠道获取最新的支付订单信息，确认是否支付
        if (BaseOrder.PaymentChannel.A_LI_PAY == marginOrder.getPaymentChannel()) {
            marginOrder = paymentService.getALiPayOrder(marginOrder);
        } else if (BaseOrder.PaymentChannel.WEIXIN_PAY == marginOrder.getPaymentChannel()) {
            marginOrder = paymentService.getWeiXinPayOrder(marginOrder);
        }

        // “待支付”状态的订单需要去找支付渠道获取最新的支付订单信息
//        if (BasePaymentOrder.Status.PENDING_PAY == marginOrder.getStatus()) {
//            if (BaseOrder.PaymentChannel.A_LI_PAY == marginOrder.getPaymentChannel()) {
//                marginOrder = paymentService.getALiPayOrder(marginOrder);
//            } else if (BaseOrder.PaymentChannel.WEIXIN_PAY == marginOrder.getPaymentChannel()) {
//                marginOrder = paymentService.getWeiXinPayOrder(marginOrder);
//            }
//        }

        // 构建返回数据
        ItemMarginOrderView view = new ItemMarginOrderView();
        view.setCreateDateTime(DateTimeUtil.getPrettyDescription(marginOrder.getCreateDateTime()));
        view.setOrderNumber(marginOrder.getOrderNumber());

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        view.setPaymentChannel(messageSource.getMessage(marginOrder.getPaymentChannel().getMessageKey(), null, request.getLocale()));
        view.setName(messageSource.getMessage("text.payment.margin.order.name", null, request.getLocale()));
        view.setStatus(messageSource.getMessage(marginOrder.getStatus().getMessageKey(), null, request.getLocale()));

        return view;
    }

    @Override
    public ItemViewPaginationList findItemsBySubCategoryCode(String subCategoryCode, int page) {
        // 校验“subCategoryCode”
        ItemSubCategory subCategory;
        if (StringUtils.isBlank(subCategoryCode)) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010103.getErrorCode());
        } else {
            subCategory = subCategoryRepository.findByCategoryCode(subCategoryCode);
            if (null == subCategory) {
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010103.getErrorCode());
            }
        }

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 二级分类：全部
        List<BaseItemCategory.SubCategory> all = new ArrayList<>();
        all.add(BaseItemCategory.SubCategory.CALLIGRAPHY_AND_PAINTING_ALL);
        all.add(BaseItemCategory.SubCategory.SIGNET_MATERIAL_ALL);
        all.add(BaseItemCategory.SubCategory.JADEWARE_ALL);
        all.add(BaseItemCategory.SubCategory.WOODEN_ALL);
        all.add(BaseItemCategory.SubCategory.SEAL_CUTTING_ALL);
        all.add(BaseItemCategory.SubCategory.CERAMIC_ALL);
        all.add(BaseItemCategory.SubCategory.LITERATURE_ALL);
        all.add(BaseItemCategory.SubCategory.OTHERS_ALL);

        // 构建“二级分类”查询条件
        List<BaseItemCategory.SubCategory> categories = new ArrayList<>();
        BaseItemCategory.SubCategory category = BaseItemCategory.SubCategory.valueOf(subCategoryCode);// 根据“二级分类的代码”查找是否属于需要查找“全部”的二级分类
        if (all.contains(category)) {// 如果“二级分类的代码”是“全部”，则查找该二级分类所对应的“一级分类”，然后再获得一级分类的所有“二级分类”
            String parentCategoryCode = StringUtils.substringBeforeLast(subCategory.getCategoryCode(), "_");
            List<ItemSubCategory> subCategories = subCategoryRepository.findByParentCategory_categoryCode(parentCategoryCode);
            for (ItemSubCategory itemSubCategory : subCategories) {
                categories.add(BaseItemCategory.SubCategory.valueOf(itemSubCategory.getCategoryCode()));// 转换成枚举类型
            }
        } else {
            categories.add(BaseItemCategory.SubCategory.valueOf(subCategoryCode));// 转换成枚举类型
        }

        return this.getItemViewsBySpecification(ItemSpecification.findBySubCategoryCode(categories), Boolean.FALSE, page);
    }

    @Override
    public ItemViewPaginationList findItemsBySearchKey(String key, int page) {
        return this.getItemViewsBySpecification(ItemSpecification.findItemsBySearchKey(key), Boolean.FALSE, page);
    }

    @Override
    public void scanItems() {
        List<Item> items = itemRepository.findAll(ItemSpecification.findAllPendingScan());
        for (Item item : items) {
            this.checkItemIsOverTime(item);
        }
    }

    /**
     * 检查拍品的“开始时间”和“结束时间”的影响，并根据规则更新拍品的状态。
     *
     * @param item 待检查的拍品
     */
    private void checkItemIsOverTime(Item item) {
        if (Item.Status.PREVIEW == item.getStatus() && DateTimeUtil.isOvertime(item.getStartDateTime())) {
            // 拍品的“倒计时”是否结束，如果结束需要将拍品的状态更新至“Item.Status.BIDDING”
            item.setStatus(Item.Status.BIDDING);
            item.setUpdateDateTime(new Date());
            itemRepository.save(item);
        } else if (Item.Status.BIDDING == item.getStatus() && DateTimeUtil.isOvertime(item.getEndDateTime())) {
            // 拍品的“竞拍结束时间”是否到了，如果竞拍结束，需要将拍品的状态更新至“Item.Status.END”
            item.setStatus(Item.Status.END);
            item.setUpdateDateTime(new Date());

            // 查找拍品的“出价竞拍记录”
            ItemBidRecord bidRecord = bidRecordRepository.findFirstByItem_idOrderByBidPriceDesc(item.getId());
            if (null != bidRecord) {
                item.setBidPrice(bidRecord.getBidPrice());// 写入到拍品的“成交价”

                // 校验当前登录用户信息
                User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
                if (null == user) {
                    throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
                }
                this.createItemOrderIfNecessary(item, user);// 同时生成订单
            }
            itemRepository.save(item);
        }
    }

    /**
     * 对拍品出价。
     *
     * @param item  出价的拍品
     * @param price 出价竞拍的价格
     */
    private ItemOrder bidItem(Item item, BigDecimal price) {
        if (null == item) {
            throw new ItemNotFoundException("要竞拍的拍品不存在！");
        }
        if (price.compareTo(BigDecimal.ZERO) == -1) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010120.getErrorCode());
        }

        // 校验传入的“price”是否为“根据合法的加价幅度所生成的价格”
        BigDecimal raisedPrice = price.subtract(item.getInitialPrice());
        if (raisedPrice.compareTo(BigDecimal.ZERO) == 1) {
            LOG.debug("raisedPrice.divide(raisedPrice): {}", raisedPrice.divide(raisedPrice, BigDecimal.ROUND_UNNECESSARY));
        } else {
            LOG.debug("raisedPrice为0！");
        }

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }
        if (user.getId() == item.getShop().getUser().getId()) {
            // 不可以对自己发布的拍品竞拍
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010121.getErrorCode());
        }

        if (item.getMargin().compareTo(BigDecimal.ZERO) == 1) {
            // 该拍品需要缴纳保证金，校验用户是否缴纳
            ItemMarginOrder marginOrder = marginOrderRepository.findByItem_idAndUser_idAndStatus(item.getId(), user.getId(), ItemMarginOrder.Status.PAID);
            if (null == marginOrder) {
                // 当前拍品需要缴纳保证金，用户还没有缴纳保证金
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010131.getErrorCode());
            }
        }

        ItemBidRecord maxBidRecord = bidRecordRepository.findFirstByItem_idOrderByBidPriceDesc(item.getId());
        if (null != maxBidRecord && maxBidRecord.getUser().getId() == user.getId()) {
            // 当前用户已经出价竞拍了，并且尚未出现价格更高的，不能再次出价竞拍
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010122.getErrorCode());
        }

        if (null != maxBidRecord && price.compareTo(maxBidRecord.getBidPrice()) < 1) {
            // 出价必须高于当前最高价
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010123.getErrorCode());
        }

        // 校验通过，新增出价竞拍记录
        List<ItemBidRecord> myItemBidRecords = bidRecordRepository.findByUser_idAndItem_id(item.getId(), user.getId());
        if (null != myItemBidRecords && !myItemBidRecords.isEmpty()) {
            bidRecordRepository.delete(myItemBidRecords);// 删除之前“我的出价竞拍记录”
        }

        // 新增“拍品出价竞拍”记录
        ItemBidRecord bidRecord = new ItemBidRecord();
        bidRecord.setItem(item);

        Date date = new Date();
        bidRecord.setUpdateDateTime(date);
        bidRecord.setCreateDateTime(date);
        bidRecord.setUser(user);
        bidRecord.setBidPrice(price);

        bidRecordRepository.save(bidRecord);

        // 1、设置了“一口价”即“一口价”大于0
        // 并且
        // 2、出价竞拍的价格大于等于“一口价”时：
        if (item.getFixedPrice().compareTo(BigDecimal.ZERO) == 1 && price.compareTo(item.getFixedPrice()) >= 0) {
            // 1. 更新“拍品”的状态
            item.setStatus(Item.Status.END);
            item.setUpdateDateTime(new Date());
            item.setFixed(Boolean.TRUE);// 表示该拍品成交于“一口价”
            item.setBidPrice(item.getFixedPrice());// 成交价格
            item = itemRepository.save(item);

            return this.createItemOrderIfNecessary(item, user);// 生成订单
        }

        return null;
    }

    /**
     * todo:javadoc
     *
     * @param item
     * @param user
     */
    private ItemOrder createItemOrderIfNecessary(Item item, User user) {
        assert null != item;

        /*
        第1步，生成“拍品订单”
        */
        ItemOrder order = new ItemOrder();
        order.setStatus(ItemOrder.Status.PENDING_PAY);
        order.setItem(item);
        order.setOrderNumber(WordEncoderUtil.encodeWordWithMD5(System.currentTimeMillis() + user.getMobile() + item.getBidPrice(), RandomUtil.generateSerialNumber()));
        order.setUser(user);

        order.setAmount(item.getBidPrice());
        if (!order.getItem().isFreeExpress()) {// 拍品“不包邮”
            if (order.getItem().getExpressFee().compareTo(BigDecimal.ZERO) == 1) {// 拍品的“邮费”大于0
                order.setExpressFee(order.getItem().getExpressFee());// 订单的邮费
            }
        }

        Date date = new Date();
        order.setCreateDateTime(date);
        order.setUpdateDateTime(date);

        // 如果用户设置了“默认的收货地址”，则在订单生成时设置到订单数据中，做为冗余数据
        ItemDeliveryAddress defaultItemDeliveryAddress = itemDeliveryAddressRepository.findByUser_idAndIsDefaultIsTrue(user.getId());
        if (null != defaultItemDeliveryAddress) {
            ItemOrderDeliveryAddress deliveryAddress = new ItemOrderDeliveryAddress();
            deliveryAddress.setDefault(defaultItemDeliveryAddress.isDefault());
            deliveryAddress.setName(defaultItemDeliveryAddress.getName());
            deliveryAddress.setProvince(defaultItemDeliveryAddress.getProvince());
            deliveryAddress.setCity(defaultItemDeliveryAddress.getCity());
            deliveryAddress.setDistrict(defaultItemDeliveryAddress.getDistrict());
            deliveryAddress.setAddress(defaultItemDeliveryAddress.getAddress());
            deliveryAddress.setMobile(defaultItemDeliveryAddress.getMobile());
            deliveryAddress.setPostcode(defaultItemDeliveryAddress.getPostcode());
            deliveryAddress.setCreateDateTime(date);
            deliveryAddress.setUpdateDateTime(date);
            deliveryAddress = itemOrderDeliveryAddressRepository.save(deliveryAddress);

            order.setDeliveryAddress(deliveryAddress);
        }
        order = itemOrderRepository.save(order);

        /*
        第2步，若该拍品是需要买家缴纳保证金，则需要退还未中标的用户缴纳的保证金，中标者的保证金需要等到支付订单成功后再进行退还
        */
        if (item.getMargin().compareTo(BigDecimal.ZERO) > 0) {
            // 查找该拍品的所有出价者的保证金
            List<ItemMarginOrder> marginOrders = marginOrderRepository.findByItem_id(item.getId());
            assert null != marginOrders && !marginOrders.isEmpty();

            // 退还未中标的用户缴纳的保证金
            for (ItemMarginOrder marginOrder : marginOrders) {
                if (marginOrder.getUser().getId() != user.getId() && BasePaymentOrder.Status.REFUNDED != marginOrder.getStatus()) {
                    // 生成一个新的用于“退还保证金”的保证金订单
                    ItemMarginOrder newOrder = ItemMarginOrderHelper.createRefundOrder(marginOrder);
                    newOrder = marginOrderRepository.save(newOrder);

                    paymentService.refundItemMarginOrder(newOrder);// 执行“退还保证金”操作 // TODO: 2016/10/31 重构：需要将该逻辑分离到队列机制中去执行
                }
            }
        }
        return order;
    }

    /**
     * 获取指定“店铺”的“已发布的拍品”的分页列表。
     *
     * @param shopSerialNumber 店铺号
     * @param page             分页
     * @return 指定“店铺”的“已发布的拍品”的分页列表
     */
    private Page<Item> findPublishedByShopSerialNameAndPage(String shopSerialNumber, int page) {
        List<Item.Status> statuses = new ArrayList<>();
        statuses.add(Item.Status.PREVIEW);
        statuses.add(Item.Status.BIDDING);
        statuses.add(Item.Status.END);
        return this.findByStatusAndPage(shopSerialNumber, page, statuses);
    }

    /**
     * 查找指定“店铺名称”和“拍品状态”的拍品分页列表。
     *
     * @param shopSerialNumber 店铺号
     * @param page             分页
     * @param statuses         拍品状态
     * @return 指定“店铺名称”和“拍品状态”的拍品分页列表
     */
    private Page<Item> findByStatusAndPage(String shopSerialNumber, int page, List<Item.Status> statuses) {
        PageUtil pageUtil = new PageUtil(PageUtil.SHOP_ITEM_PAGE_SIZE);// 设置每页的记录数
        page = pageUtil.getPageForPageable(page);

        Pageable pageable = new PageRequest(page, pageUtil.getPageSize());

        Page<Item> items = itemRepository.findAll(ItemSpecification.findAllByShopSerialNumberAndStatus(shopSerialNumber, statuses), pageable);
        LOG.debug("本次查询条件总共 {} 页。", items.getTotalPages());
        return items;
    }

    /**
     * 根据给定的查询条件进行查询，并将查询结果转换成“ItemViewPaginationList”。
     *
     * @param specification   查询条件
     * @param isNeedBidRecord 是否需要“出价竞拍记录”，做“竞拍排行”使用
     * @param page            分页
     * @return 查询结果
     */
    private ItemViewPaginationList getItemViewsBySpecification(Specification<Item> specification, boolean isNeedBidRecord, int page) {
        PageUtil pageUtil = new PageUtil(PageUtil.SHOP_ITEM_PAGE_SIZE);// 设置每页的记录数
        page = pageUtil.getPageForPageable(page);

        Pageable pageable = new PageRequest(page, pageUtil.getPageSize());

        Page<Item> itemPage = itemRepository.findAll(specification, pageable);
        LOG.debug("本次查询条件总共 {} 页。", itemPage.getTotalPages());

        List<Item> items = itemPage.getContent();
        ItemViewPaginationList paginationList = new ItemViewPaginationList();
        paginationList.setTotalPages(String.valueOf(itemPage.getTotalPages()));
        paginationList.setTotalRecords(String.valueOf(itemPage.getTotalElements()));

        // 下一页的页数，这里"+1"的目的是还原当前的页数，而不是查询结果里的页数
        int nextPage = itemPage.getNumber() + 1;

        // 如果还有下一页，则继续"+1"返回下一页的页数
        if (itemPage.hasNext()) {
            nextPage = nextPage + 1;
        }
        LOG.debug("itemPage.getNumber(): {}", itemPage.getNumber());
        LOG.debug("itemPage.getNumberOfElements(): {}", itemPage.getNumberOfElements());
        paginationList.setNextPage(String.valueOf(nextPage));
        if (null != items) {
            paginationList.getItemViews().addAll(this.getItemViewsFromItems(items, isNeedBidRecord));
        }
        return paginationList;
    }

    /**
     * @param items           “Item”列表
     * @param isNeedBidRecord “ItemView”里是否需要“出价竞拍”
     * @return
     */
    public List<ItemView> getItemViewsFromItems(List<Item> items, boolean isNeedBidRecord) {
        List<ItemView> itemViews = new ArrayList<>();
        if (null != items) {
            for (Item item : items) {
                this.checkItemIsOverTime(item);
                itemViews.add(this.getItemViewFromItem(item, isNeedBidRecord));
            }
        }
        return itemViews;
    }

    /**
     * todo:javadoc
     *
     * @param status
     * @param page
     * @return
     */
    private ItemViewPaginationList getItemViewPaginationListByStatus(Item.Status status, int page) {
        PageUtil pageUtil = new PageUtil(PageUtil.SHOP_ITEM_PAGE_SIZE);// 设置每页的记录数
        page = pageUtil.getPageForPageable(page);

        Pageable pageable = new PageRequest(page, pageUtil.getPageSize());

        Page<Item> itemPage = itemRepository.findByStatusOrderByCreateDateTimeDescIdDesc(status, pageable);
        LOG.debug("本次查询条件总共 {} 页。", itemPage.getTotalPages());

        List<Item> items = itemPage.getContent();
        ItemViewPaginationList paginationList = new ItemViewPaginationList();
        paginationList.setTotalPages(String.valueOf(itemPage.getTotalPages()));
        paginationList.setTotalRecords(String.valueOf(itemPage.getTotalElements()));

        // 下一页的页数，这里"+1"的目的是还原当前的页数，而不是查询结果里的页数
        int nextPage = itemPage.getNumber() + 1;

        // 如果还有下一页，则继续"+1"返回下一页的页数
        if (itemPage.hasNext()) {
            nextPage = nextPage + 1;
        }
        LOG.debug("itemPage.getNumber(): {}", itemPage.getNumber());
        LOG.debug("itemPage.getNumberOfElements(): {}", itemPage.getNumberOfElements());
        paginationList.setNextPage(String.valueOf(nextPage));
        if (null != items) {
            paginationList.getItemViews().addAll(this.getItemViewsFromItems(items, Boolean.FALSE));
        }
        return paginationList;
    }

    /**
     * todo:javadoc
     *
     * @param item
     * @return
     */
    private ItemDetailView getItemDetailView(Item item, User user) {
        this.checkItemIsOverTime(item);
        ItemDetailView view = conversionService.convert(item, ItemDetailView.class);
        view.setShowAuditButtons(Item.Status.PENDING_REVIEW == item.getStatus());

        List<ItemBidRecord> bidRecords = bidRecordRepository.findByItem_idOrderByBidPriceDesc(item.getId());
        if (null != bidRecords && !bidRecords.isEmpty()) {
            List<ItemBidRecordView> bidRecordViews = new ArrayList<>();
            for (int i = 0; i < bidRecords.size(); i++) {
                ItemBidRecord bidRecord = bidRecords.get(i);

                // 构建“拍品的竞拍排行”
                ItemBidRecordView bidRecordView = new ItemBidRecordView();
                bidRecordView.setBidPrice(String.valueOf(bidRecord.getBidPrice()));
                bidRecordView.setNickname(bidRecord.getUser().getProfile().getNickname());
                bidRecordView.setOrder(String.valueOf(i + 1));
                bidRecordView.setUserAvatarUrl(UserHelper.getAvatar3xUrl(bidRecord.getUser()));

                bidRecordViews.add(bidRecordView);

                if (i == 0) {
                    view.setMaxBidPrice(bidRecord.getBidPrice().toString());// 设置“当前最大竞拍价格”
                }
            }
            view.setBidRecords(bidRecordViews);
        }

        List<ItemImageView> imageViews = new ArrayList<>();
        if (null != item.getImages()) {
            Merchant merchant = item.getShop().getMerchant();
            for (ItemImage itemImage : item.getImages()) {
                ItemImageView imageView = new ItemImageView();
                try {
                    imageView.setUrl(ImageUtil.getItemImageUrlPrefix(DateFormatUtils.format(merchant.getCreateDateTime(), DateTimeUtil.DATE_FORMAT_ALL), merchant.getRealName(), merchant.getIdentityNumber(), merchant.getUser().getId(), merchant.getUser().getSerialNumber()) + "/shop/items/" + itemImage.getImageName());
                } catch (ConfigurationException e) {
                    LOG.error(e.getMessage(), e);
                }

                imageViews.add(imageView);
            }
        }
        view.setImages(imageViews);

        view.setBrowseTimes(String.valueOf(itemBrowseRecordRepository.countByItem_id(item.getId())));// 拍品的浏览次数
        view.setFavoritesTimes(String.valueOf(favoriteItemRepository.countByItem_id(item.getId())));// 拍品的收藏次数

        if (null != user) {
            // 当前登录用户是否收藏了该拍品
            List<MyFavoriteItem> favoriteItems = favoriteItemRepository.findByUser_idAndItem_id(user.getId(), item.getId());
            if (null != favoriteItems && !favoriteItems.isEmpty()) {
                view.setIsFavorite("true");
            } else {
                view.setIsFavorite("false");
            }

            // 记录拍品的浏览记录
            ItemBrowseRecord browseRecord = new ItemBrowseRecord();
            browseRecord.setItem(item);
            browseRecord.setUser(user);

            Date date = new Date();// 生成时间，保证该数据需要用到时间的地方的数据一致性
            browseRecord.setCreateDateTime(date);
            browseRecord.setUpdateDateTime(date);

            itemBrowseRecordRepository.save(browseRecord);// 保存浏览记录
        }

        return view;
    }

    /**
     * todo:javadoc
     *
     * @param itemId
     * @return
     */
    private Item checkItemId(String itemId) {
        if (StringUtils.isBlank(itemId)) {
            throw new ItemNotFoundException("没有找到指定“itemId”的拍品：" + itemId);
        }
        Item item = itemRepository.findOne(NumberUtils.toLong(itemId));
        if (null == item) {
            throw new ItemNotFoundException("没有找到指定“itemId”的拍品：" + itemId);
        }
        return item;
    }

    /**
     * 判断指定拍品是否允许一口价。
     *
     * @param fixedPrice 拍品的一口价
     * @return 允许一口价的话返回“FIXED_PRICE”，否则返回“BID_PRICE”
     */
    private String isFixedPrice(BigDecimal fixedPrice) {
        final String fixedPriceShow = "FIXED_PRICE";// 显示“一口价/出价”
        final String bidPriceShow = "BID_PRICE";// 显示“出价”
        // 该拍品是否一口价
        if (fixedPrice.compareTo(BigDecimal.ZERO) == 1) {
            return fixedPriceShow;// 一口价
        } else {
            return bidPriceShow;
        }
    }
}