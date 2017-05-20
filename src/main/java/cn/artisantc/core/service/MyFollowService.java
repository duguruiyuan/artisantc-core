package cn.artisantc.core.service;

import cn.artisantc.core.web.rest.v1_0.vo.pagination.MyFollowPaginationList;

/**
 * 支持“我的关注”操作的服务接口。
 * Created by xinjie.li on 2016/9/19.
 *
 * @author xinjie.li
 * @since 1.0
 */
public interface MyFollowService {


    /**
     * 根据给定的页数获取我的关注列表的分页结果，默认返回第1页的结果。
     *
     * @param page 分页
     * @return 指定页数的结果列表，当指定页数<=0时返回第1页的结果
     */
    MyFollowPaginationList findByPage(int page);

    /**
     * “关注/取消关注”。
     *
     * @param followUserId “关注/取消关注”用户的ID
     */
    void follow(String followUserId);

    /**
     * “关注大咖”，可以一次关注多个大咖。
     *
     * @param followUserIds “关注大咖”的用户的ID
     */
    void followArtBigShots(String[] followUserIds);
}
