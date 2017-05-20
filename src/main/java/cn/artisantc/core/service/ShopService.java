package cn.artisantc.core.service;

import cn.artisantc.core.web.rest.v1_0.vo.ShopView;
import cn.artisantc.core.web.rest.v1_0.vo.UpdateShopAvatarView;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.ItemViewPaginationList;
import org.springframework.web.multipart.MultipartFile;

/**
 * 支持“店铺”操作的服务接口。
 * Created by xinjie.li on 2016/9/24.
 *
 * @author xinjie.li
 * @since 1.0
 */
public interface ShopService {

    /**
     * 根据指定的“店铺号”查找店铺的信息。
     *
     * @return 店铺的信息
     */
    ShopView findMyShop();

    /**
     * 修改店铺的名称。
     *
     * @param name 店铺要修改的新名称
     */
    void updateMyShopName(String name);

    /**
     * 修改店铺的头像。
     *
     * @param files 店铺要修改的新头像
     * @return 新头像的URL地址
     */
    UpdateShopAvatarView updateMyShopAvatar(MultipartFile[] files);

    /**
     * 根据“店铺号”查找店铺信息。
     *
     * @param serialNumber 店铺号
     * @return 指定“店铺号”的店铺信息
     */
    ShopView findBySerialNumber(String serialNumber);

    /**
     * 校验“我是否已经开店”。
     *
     * @return 是否已经开店的结果，已经开店返回true，否则返回false
     */
    boolean hasSetUpShop();

    /**
     * 开店，开设我的店铺。
     */
    void setUpMyShop();

    /**
     * 获得“我的店铺中的拍品(待审核)”。
     *
     * @param page 分页
     * @return “我的店铺中的拍品(待审核)”列表
     */
    ItemViewPaginationList findMyPendingReviewItems(int page);

    /**
     * 获得“我的店铺中的拍品(预展中)”。
     *
     * @param page 分页
     * @return “我的店铺中的拍品(预展中)”列表
     */
    ItemViewPaginationList findMyPreviewItems(int page);

    /**
     * 获得“我的店铺中的拍品(竞拍中)”。
     *
     * @param page 分页
     * @return “我的店铺中的拍品(竞拍中)”列表
     */
    ItemViewPaginationList findMyBiddingItems(int page);

    /**
     * 获得“我的店铺中的拍品(已结束)”。
     *
     * @param page 分页
     * @return “我的店铺中的拍品(已结束)”列表
     */
    ItemViewPaginationList findMyEndItems(int page);
}
