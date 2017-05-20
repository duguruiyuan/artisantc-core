package cn.artisantc.core.web.rest.v1_0.vo;

/**
 * “艺术大咖”的视图对象。
 * Created by xinjie.li on 2016/12/30.
 *
 * @author xinjie.li
 * @since 2.1
 */
public class ArtBigShotView extends BaseUserView {

    private String overview;// 大咖的一句话简介

    private String introduce;// 大咖的详细介绍

    private String artBigShotId;// 大咖ID

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getArtBigShotId() {
        return artBigShotId;
    }

    public void setArtBigShotId(String artBigShotId) {
        this.artBigShotId = artBigShotId;
    }
}
