package cn.artisantc.core.service;

import cn.artisantc.core.exception.ShopNotFoundException;
import cn.artisantc.core.persistence.entity.MyFavoriteShop;
import cn.artisantc.core.persistence.entity.Shop;
import cn.artisantc.core.persistence.entity.User;
import cn.artisantc.core.persistence.helper.UserHelper;
import cn.artisantc.core.persistence.repository.MyFavoriteShopRepository;
import cn.artisantc.core.persistence.repository.OAuth2Repository;
import cn.artisantc.core.persistence.repository.ShopRepository;
import cn.artisantc.core.util.AvatarUtil;
import cn.artisantc.core.util.ConverterUtil;
import cn.artisantc.core.util.DateTimeUtil;
import cn.artisantc.core.util.PageUtil;
import cn.artisantc.core.web.rest.v1_0.vo.MyFavoriteShopView;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.MyFavoriteShopPaginationList;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * “MyFavoriteShopService”接口的实现类。
 * Created by xinjie.li on 2016/10/14.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Service
@Transactional
public class MyFavoriteShopServiceImpl implements MyFavoriteShopService {

    private static final Logger LOG = LoggerFactory.getLogger(MyFavoriteShopServiceImpl.class);

    private OAuth2Repository oAuth2Repository;

    private MyFavoriteShopRepository favoriteShopRepository;

    private ShopRepository shopRepository;

    @Autowired
    public MyFavoriteShopServiceImpl(OAuth2Repository oAuth2Repository, MyFavoriteShopRepository favoriteShopRepository,
                                     ShopRepository shopRepository) {
        this.oAuth2Repository = oAuth2Repository;
        this.favoriteShopRepository = favoriteShopRepository;
        this.shopRepository = shopRepository;
    }

    @Override
    public MyFavoriteShopPaginationList findByPage(int page) {
        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，获取屏蔽列表操作终止！请尝试重新登录后再进行。");
        }

        // 查询数据
        PageUtil pageUtil = new PageUtil(PageUtil.MY_FAVORITE_PAGE_SIZE);
        page = pageUtil.getPageForPageable(page);
        Pageable pageable = new PageRequest(page, pageUtil.getPageSize());

        Page<MyFavoriteShop> favoritePage = favoriteShopRepository.findByUser_idOrderByIdDesc(user.getId(), pageable);
        List<MyFavoriteShop> favorites = favoritePage.getContent();

        // 重新组装数据
        MyFavoriteShopPaginationList paginationList = new MyFavoriteShopPaginationList();
        paginationList.setTotalPages(String.valueOf(favoritePage.getTotalPages()));
        paginationList.setTotalRecords(String.valueOf(favoritePage.getTotalElements()));

        // 下一页的页数，这里"+1"的目的是还原当前的页数，而不是查询结果里的页数
        int nextPage = favoritePage.getNumber() + 1;

        // 如果还有下一页，则继续"+1"返回下一页的页数
        if (favoritePage.hasNext()) {
            nextPage = nextPage + 1;
        }
        LOG.debug("favoritePage.getNumber(): {}", favoritePage.getNumber());
        LOG.debug("favoritePage.getNumberOfElements(): {}", favoritePage.getNumberOfElements());
        paginationList.setNextPage(String.valueOf(nextPage));

        // 遍历查询结果列表，并进行类型转换
        if (null != favorites) {
            List<MyFavoriteShopView> favoriteShopViews = new ArrayList<>();
            for (MyFavoriteShop favorite : favorites) {
                MyFavoriteShopView view = new MyFavoriteShopView();

                try {
                    view.setAvatarUrl(AvatarUtil.getShopAvatarUrlPrefix() + ConverterUtil.getMerchantEncodeString(DateFormatUtils.format(favorite.getShop().getMerchant().getCreateDateTime(), DateTimeUtil.DATE_FORMAT_ALL), favorite.getShop().getMerchant().getRealName(), favorite.getShop().getMerchant().getIdentityNumber(), favorite.getShop().getMerchant().getUser().getId(), favorite.getShop().getMerchant().getUser().getSerialNumber()) + "/" + "shop" + "/" + favorite.getShop().getAvatarFileName());// 店铺头像地址
                } catch (ConfigurationException e) {
                    LOG.error(e.getMessage(), e);
                }

                view.setName(favorite.getShop().getName());
                view.setSerialNumber(favorite.getShop().getSerialNumber());

                favoriteShopViews.add(view);
            }
            paginationList.getFavoriteShopViews().addAll(favoriteShopViews);
        }
        return paginationList;
    }

    @Override
    public void favorite(String serialNumber) {
        // 校验“店铺号”
        if (StringUtils.isBlank(serialNumber)) {
            throw new ShopNotFoundException("");
        } else if (!NumberUtils.isParsable(serialNumber)) {
            throw new ShopNotFoundException("没有找到指定“serialNumber”对应的资源：" + serialNumber);
        }

        // 校验当前登录用户信息
        User user = UserHelper.getCurrentLoginUser(oAuth2Repository);
        if (null == user) {
            throw new UsernameNotFoundException("没有获取到当前登录用户的信息，获取点赞列表操作终止！请尝试重新登录后再进行。");
        }

        // 加载店铺
        Shop shop = shopRepository.findBySerialNumber(serialNumber);
        if (null == shop) {
            // 没有找到给定“serialNumber”的店铺
            throw new ShopNotFoundException("没有找到指定“serialNumber”对应的资源：" + serialNumber);
        }

        // 查找是否已经收藏
        List<MyFavoriteShop> favoriteShops = favoriteShopRepository.findByUser_idAndShop_id(user.getId(), shop.getId());
        if (null == favoriteShops || favoriteShops.isEmpty()) {
            // 没有收藏，执行“收藏”操作
            MyFavoriteShop favorite = new MyFavoriteShop();
            Date date = new Date();// 生成时间，保证该数据需要用到时间的地方的数据一致性
            favorite.setUpdateDateTime(date);
            favorite.setCreateDateTime(date);
            favorite.setShop(shop);
            favorite.setUser(user);

            favoriteShopRepository.save(favorite);
        } else {
            // 已经收藏，执行“取消收藏”操作
            assert favoriteShops.size() == 1;
            MyFavoriteShop favorite = favoriteShops.iterator().next();
            favoriteShopRepository.delete(favorite.getId());// 取消收藏
        }
    }
}
