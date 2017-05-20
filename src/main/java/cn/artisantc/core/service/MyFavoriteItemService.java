package cn.artisantc.core.service;

import cn.artisantc.core.web.rest.v1_0.vo.pagination.MyFavoriteItemPaginationList;

/**
 * 支持“我收藏的拍品”操作的服务接口。
 * Created by xinjie.li on 2016/10/14.
 *
 * @author xinjie.li
 * @since 1.0
 */
public interface MyFavoriteItemService {

    /**
     * 根据给定的页数获取“我收藏的拍品”的分页结果，默认返回第1页的结果。
     *
     * @param page 分页
     * @return 指定页数的结果列表，当指定页数<=0时返回第1页的结果
     */
    MyFavoriteItemPaginationList findByPage(int page);

    /**
     * 添加拍品到我的收藏。
     *
     * @param itemId 拍品ID
     */
    void favorite(String itemId);

    /**
     * 根据给定的页数获取“开拍提醒”的分页结果，默认返回第1页的结果。
     *
     * @param page 分页
     * @return 指定页数的结果列表，当指定页数<=0时返回第1页的结果
     */
    MyFavoriteItemPaginationList findMyUpcomingBiddingItems(int page);
}
