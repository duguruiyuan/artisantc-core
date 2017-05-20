package cn.artisantc.core.service;

import cn.artisantc.core.web.rest.v1_0.vo.pagination.MyFavoriteArtMomentPaginationList;

/**
 * 支持“我收藏的艺文”操作的服务接口。
 * Created by xinjie.li on 2016/9/20.
 *
 * @author xinjie.li
 * @since 1.0
 */
public interface MyFavoriteArtMomentService {

    /**
     * 添加艺文到我的收藏。
     *
     * @param momentId 艺文ID
     */
    void favorite(String momentId);

    /**
     * 根据给定的页数获取“我收藏的艺文”的分页结果，默认返回第1页的结果。
     *
     * @param page 分页
     * @return 指定页数的结果列表，当指定页数<=0时返回第1页的结果
     */
    MyFavoriteArtMomentPaginationList findByPage(int page);
}
