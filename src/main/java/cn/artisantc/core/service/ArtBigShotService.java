package cn.artisantc.core.service;

import cn.artisantc.core.web.rest.v1_0.vo.ArtBigShotView;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.ArtBigShotViewPaginationList;

import java.util.List;

/**
 * 支持“艺术大咖”操作的服务接口。
 * Created by xinjie.li on 2016/12/30.
 *
 * @author xinjie.li
 * @since 2.1
 */
public interface ArtBigShotService {

    /**
     * 随机获得“推荐大咖”列表。
     *
     * @return 随机“推荐大咖”列表
     */
    List<ArtBigShotView> getRecommendArtBigShot();

    /**
     * 获得“大咖列表”。
     *
     * @param page 分页
     * @return “大咖列表”
     */
    ArtBigShotViewPaginationList findArtBigShots(int page);

    /**
     * “成为大咖”，让指定用户成为大咖。
     *
     * @param userId    “成为大咖”的指定用户ID。
     * @param overview  大咖的一句话简介
     * @param introduce 大咖的详细介绍
     */
    void upgradeToArtBigShot(String userId, String overview, String introduce);

    /**
     * 获得指定“大咖”的信息。
     *
     * @param artBigShotId 大咖ID
     * @return 指定“大咖”的信
     */
    ArtBigShotView findArtBigShotById(String artBigShotId);

    /**
     * 取消指定“大咖资格”。
     *
     * @param artBigShotId 大咖ID
     */
    void cancelArtBigShot(String artBigShotId);
}
