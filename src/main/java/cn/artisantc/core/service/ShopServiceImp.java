package cn.artisantc.core.service;

import cn.artisantc.core.exception.ShopNotFoundException;
import cn.artisantc.core.persistence.entity.Merchant;
import cn.artisantc.core.persistence.entity.MyFavoriteShop;
import cn.artisantc.core.persistence.entity.Shop;
import cn.artisantc.core.persistence.entity.User;
import cn.artisantc.core.persistence.helper.UserHelper;
import cn.artisantc.core.persistence.repository.MerchantRepository;
import cn.artisantc.core.persistence.repository.MyFavoriteShopRepository;
import cn.artisantc.core.persistence.repository.OAuth2Repository;
import cn.artisantc.core.persistence.repository.ShopRepository;
import cn.artisantc.core.util.AvatarUtil;
import cn.artisantc.core.util.ConverterUtil;
import cn.artisantc.core.util.DateTimeUtil;
import cn.artisantc.core.util.ImageUtil;
import cn.artisantc.core.util.PageUtil;
import cn.artisantc.core.web.rest.v1_0.vo.APIErrorResponse;
import cn.artisantc.core.web.rest.v1_0.vo.ShopView;
import cn.artisantc.core.web.rest.v1_0.vo.UpdateShopAvatarView;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.ItemViewPaginationList;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * “ShopService”接口的实现类。
 * Created by xinjie.li on 2016/9/24.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Service
@Transactional
public class ShopServiceImp implements ShopService {

    private static final Logger LOG = LoggerFactory.getLogger(ShopServiceImp.class);

    private OAuth2Repository oAuth2Repository;

    private ShopRepository shopRepository;

    private ItemService itemService;

    private MerchantRepository merchantRepository;

    private MerchantService merchantService;

    private MyFavoriteShopRepository favoriteShopRepository;

    @Autowired
    public ShopServiceImp(OAuth2Repository oAuth2Repository, ShopRepository shopRepository, ItemService itemService,
                          MerchantRepository merchantRepository, MerchantService merchantService, MyFavoriteShopRepository favoriteShopRepository) {
        this.oAuth2Repository = oAuth2Repository;
        this.shopRepository = shopRepository;
        this.itemService = itemService;
        this.merchantRepository = merchantRepository;
        this.merchantService = merchantService;
        this.favoriteShopRepository = favoriteShopRepository;
    }

    @Override
    public ShopView findMyShop() {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 查找“我的店铺”
        Shop shop = shopRepository.findByUser_id(user.getId());
        if (null == shop) {
            // 没有找到给定“name”的店铺
            throw new ShopNotFoundException("没有找到“userId”对应的店铺资源：" + user.getId());
        }
        return this.getMyShopView(shop);
    }

    @Override
    public void updateMyShopName(String name) {
        // 校验输入的内容“name”
        if (StringUtils.isNotBlank(name) && name.length() > APIErrorResponse.MAX_SHOP_NAME_LENGTH) {
            // “店铺名称”不能超过10个中文字符
            LOG.debug("name.length(): {}", name.length());
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010101.getErrorCode());
        }

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }
        Shop shop = shopRepository.findByUser_id(user.getId());
        if (null == shop) {
            // 没有找到给定“userId”的店铺
            throw new ShopNotFoundException("没有找到“userId”对应的店铺资源：" + user.getId());
        }

        if (shop.getCreateDateTime().compareTo(shop.getUpdateDateTime()) != 0 && DateTimeUtil.isThisYear(shop.getUpdateDateTime())) {
            // 创建时间和更新时间不一样，并且今年已经修改过店铺名称，则不允许修改，抛出异常
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010119.getErrorCode());
        }

        // 通过“允许修改检查”，执行修改
        shop.setName(name);
        shop.setUpdateDateTime(new Date());
        shopRepository.save(shop);
    }

    @Override
    public ShopView findBySerialNumber(String serialNumber) {
        // 校验“店铺号”
        if (StringUtils.isBlank(serialNumber)) {
            throw new ShopNotFoundException("没有找到指定“serialNumber”对应的店铺资源：" + serialNumber);
        }

        Shop shop = shopRepository.findBySerialNumber(serialNumber);
        if (null == shop) {
            throw new ShopNotFoundException("没有找到指定“serialNumber”对应的店铺资源：" + serialNumber);
        }
        return this.getShopView(shop);
    }

    @Override
    public UpdateShopAvatarView updateMyShopAvatar(MultipartFile[] files) {
        // 校验新的“店铺头像”
        List<MultipartFile> uploadedFiles = new ArrayList<>();// 真正提交过来准备上传的图片
        if (null == files) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010118.getErrorCode());
        } else {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    if (!ImageUtil.isPicture(file)) {
                        throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E990031.getErrorCode());
                    }
                    uploadedFiles.add(file);
                }
            }
            if (uploadedFiles.size() != APIErrorResponse.SHOP_AVATAR_LENGTH) {
                throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010118.getErrorCode());
            }
        }

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }
        Shop shop = shopRepository.findByUser_id(user.getId());
        if (null == shop) {
            // 没有找到给定“userId”的店铺
            throw new ShopNotFoundException("没有找到“userId”对应的店铺资源：" + user.getId());
        }

        // 尝试获取店铺原先的头像
        Merchant merchant = merchantRepository.findByUser_id(user.getId());
        MultipartFile file = uploadedFiles.iterator().next();
        String path;// 店铺头像的存储路径
        try {
            path = ImageUtil.getMerchantStorePath(DateFormatUtils.format(merchant.getCreateDateTime(), DateTimeUtil.DATE_FORMAT_ALL), merchant.getRealName(), merchant.getIdentityNumber(), merchant.getUser().getId(), merchant.getUser().getSerialNumber()) + File.separatorChar + "shop" + File.separatorChar;
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

        // 上传新的店铺头像
        long fileNamePrefix = System.currentTimeMillis();
        String tempFileName = fileNamePrefix + "_" + user.getSerialNumber() + "_temp.jpg";// 临时文件名
        String newFileName = fileNamePrefix + "_" + user.getSerialNumber() + "_3x.jpg";// 生成存储到本地的文件名，生成的是3X图片，并且图片类型固定为“jpg”

        File tempFile = new File(path + tempFileName);// 将用户上传的文件存储起来，生成临时文件
        try {
            file.transferTo(tempFile);
            BufferedImage bufferedImage = ImageIO.read(tempFile);
            int newWidth = ImageUtil.SHOP_AVATAR_WIDTH;// 默认宽度
            if (bufferedImage.getWidth() < ImageUtil.SHOP_AVATAR_WIDTH) {// 若用户上传的图片的宽度小于系统的“默认宽度”，则使用用户上传的图片的宽度
                newWidth = bufferedImage.getWidth();
            }
            ImageUtil.zoomImageScale(bufferedImage, path + newFileName, newWidth);// 重新(经过压缩)生成头像的“3X图”

            // 删除临时文件
            if (tempFile.delete()) {
                LOG.info("店铺头像的临时文件删除成功： {})", path + tempFileName);
            } else {
                LOG.info("店铺头像的临时文件删除失败：： {})", path + tempFileName);
            }
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E990030.getErrorCode());
        }

        // 找到了店铺原先的头像，执行删除操作
        File oldAvatar = new File(path + shop.getAvatarFileName());
        if (oldAvatar.delete()) {
            LOG.info("店铺原先的头像删除成功：{} (店铺号： {})", path + shop.getAvatarFileName(), shop.getSerialNumber());
        } else {
            LOG.info("店铺原先的头像删除失败：{} (店铺号： {})", path + shop.getAvatarFileName(), shop.getSerialNumber());
        }

        // 更新店铺头像的文件名
        shop.setUpdateDateTime(new Date());
        shop.setAvatarFileName(newFileName);

        shop = shopRepository.save(shop);

        // 构建返回数据
        UpdateShopAvatarView view = new UpdateShopAvatarView();
        try {
            view.setAvatarUrl(AvatarUtil.getShopAvatarUrlPrefix() + ConverterUtil.getMerchantEncodeString(DateFormatUtils.format(merchant.getCreateDateTime(), DateTimeUtil.DATE_FORMAT_ALL), merchant.getRealName(), merchant.getIdentityNumber(), merchant.getUser().getId(), merchant.getUser().getSerialNumber()) + "/" + "shop" + "/" + shop.getAvatarFileName());// 店铺头像地址
        } catch (ConfigurationException e) {
            LOG.error(e.getMessage(), e);
        }
        return view;
    }

    @Override
    public boolean hasSetUpShop() {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 查找“我的店铺”
        Shop shop = shopRepository.findByUser_id(user.getId());
        return null != shop;
    }

    @Override
    public void setUpMyShop() {
        // 校验“是否已经通过实名认证”
        if (!merchantService.hasApprovedRealName()) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010129.getErrorCode());
        }

        // 校验“是否已经开店”
        if (this.hasSetUpShop()) {
            throw new IllegalArgumentException(APIErrorResponse.ErrorCode.E010130.getErrorCode());
        }

        // 开店！
        merchantService.createMerchant();
    }

    @Override
    public ItemViewPaginationList findMyPendingReviewItems(int page) {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 查找“我的店铺”
        Shop shop = shopRepository.findByUser_id(user.getId());
        if (null == shop) {
            // 没有找到给定“name”的店铺
            throw new ShopNotFoundException("没有找到“userId”对应的店铺资源：" + user.getId());
        }

        return itemService.findPendingReviewByShopSerialNumberAndPage(shop.getSerialNumber(), page);
    }

    @Override
    public ItemViewPaginationList findMyPreviewItems(int page) {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 查找“我的店铺”
        Shop shop = shopRepository.findByUser_id(user.getId());
        if (null == shop) {
            // 没有找到给定“name”的店铺
            throw new ShopNotFoundException("没有找到“userId”对应的店铺资源：" + user.getId());
        }

        return itemService.findPreviewByShopSerialNumberAndPage(shop.getSerialNumber(), page);
    }

    @Override
    public ItemViewPaginationList findMyBiddingItems(int page) {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 查找“我的店铺”
        Shop shop = shopRepository.findByUser_id(user.getId());
        if (null == shop) {
            // 没有找到给定“name”的店铺
            throw new ShopNotFoundException("没有找到“userId”对应的店铺资源：" + user.getId());
        }

        return itemService.findBiddingByShopSerialNumberAndPage(shop.getSerialNumber(), page);
    }

    @Override
    public ItemViewPaginationList findMyEndItems(int page) {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        // 查找“我的店铺”
        Shop shop = shopRepository.findByUser_id(user.getId());
        if (null == shop) {
            // 没有找到给定“name”的店铺
            throw new ShopNotFoundException("没有找到“userId”对应的店铺资源：" + user.getId());
        }

        return itemService.findEndByShopSerialNumberAndPage(shop.getSerialNumber(), page);
    }

    /**
     * 将“店铺(Shop)”转换为“店铺视图(ShopView)”。
     *
     * @param shop 待转换的店铺
     * @return 转换后的
     */
    private ShopView getShopView(Shop shop) {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，操作终止！请尝试重新登录后再进行。");
        }

        ItemViewPaginationList itemViewPaginationList = itemService.findByShopSerialNumberAndPage(shop.getSerialNumber(), PageUtil.FIRST_PAGE);

        // 查找当前登录用户是否收藏了该店铺
        List<MyFavoriteShop> favoriteShops = favoriteShopRepository.findByUser_idAndShop_id(user.getId(), shop.getId());

        return this.buildShopView(itemViewPaginationList, shop, (null != favoriteShops && !favoriteShops.isEmpty()), shop.getUser().getId() == user.getId());
    }

    /**
     * 获得“我的店铺”信息。
     *
     * @param shop 我的店铺
     * @return “我的店铺”信息
     */
    private ShopView getMyShopView(Shop shop) {
        ItemViewPaginationList itemViewPaginationList = itemService.findMyItemsByPage(PageUtil.FIRST_PAGE);

        return this.buildShopView(itemViewPaginationList, shop, true, true);
    }

    /**
     * 构建“店铺视图(ShopView)”。
     *
     * @param itemViewPaginationList 店铺所拥有的拍品
     * @param shop                   店铺
     * @return 构建后的“店铺视图(ShopView)”
     */
    private ShopView buildShopView(ItemViewPaginationList itemViewPaginationList, Shop shop, boolean isFavorite, boolean isMyShop) {
        Merchant merchant = shop.getMerchant();
        // 构建返回数据
        ShopView view = new ShopView();

        view.setTotalItems(itemViewPaginationList.getTotalRecords());
        view.setItemViews(itemViewPaginationList.getItemViews());

        try {
            view.setAvatarUrl(AvatarUtil.getShopAvatarUrlPrefix() + ConverterUtil.getMerchantEncodeString(DateFormatUtils.format(merchant.getCreateDateTime(), DateTimeUtil.DATE_FORMAT_ALL), merchant.getRealName(), merchant.getIdentityNumber(), merchant.getUser().getId(), merchant.getUser().getSerialNumber()) + "/" + "shop" + "/" + shop.getAvatarFileName());// 店铺头像地址
        } catch (ConfigurationException e) {
            LOG.error(e.getMessage(), e);
        }

        view.setName(shop.getName());
        view.setSerialNumber(shop.getSerialNumber());
        view.setTotalFans(String.valueOf(favoriteShopRepository.countByShop_id(shop.getId())));// 计算店铺的粉丝数量
        view.setIsFavorite(String.valueOf(isFavorite));
        view.setIsMyShop(String.valueOf(isMyShop));

        return view;
    }
}
