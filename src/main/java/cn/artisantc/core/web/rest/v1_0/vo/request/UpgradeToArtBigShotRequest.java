package cn.artisantc.core.web.rest.v1_0.vo.request;

/**
 * “成为大咖”请求的视图对象。
 * Created by xinjie.li on 2017/1/4.
 *
 * @author xinjie.li
 * @since 2.1
 */
public class UpgradeToArtBigShotRequest {

    private String userId;

    private String introduce;// 大咖的详细介绍

    private String overview;// 大咖的一句话简介

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}
