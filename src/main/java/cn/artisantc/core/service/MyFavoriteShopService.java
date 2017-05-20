package cn.artisantc.core.service;

import cn.artisantc.core.web.rest.v1_0.vo.pagination.MyFavoriteShopPaginationList;

/**
 * 支持“我收藏的店铺”操作的服务接口。
 * Created by xinjie.li on 2016/10/14.
 *
 * @author xinjie.li
 * @since 1.0
 */
public interface MyFavoriteShopService {

    /**
     * 根据给定的页数获取“我收藏的店铺”的分页结果，默认返回第1页的结果。
     *
     * @param page 分页
     * @return 指定页数的结果列表，当指定页数<=0时返回第1页的结果
     */
    MyFavoriteShopPaginationList findByPage(int page);

    /**
     * 添加店铺到我的收藏。
     *
     * @param serialNumber 店铺号
     */
    void favorite(String serialNumber);
}
