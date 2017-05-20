package cn.artisantc.core.web.rest.v1_0.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * “广告详情”的视图对象。
 * Created by xinjie.li on 2016/11/15.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class AdvertisementDetailView {

    private String title;// 广告的标题

    private String coverUrl = "";// 广告的封面图片的URL地址

    @JsonProperty(value = "advertisementId")
    private String id;// 广告ID

    private String browseTimes = "0";// 浏览次数

    private String createDateTime;// 广告的发布时间

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrowseTimes() {
        return browseTimes;
    }

    public void setBrowseTimes(String browseTimes) {
        this.browseTimes = browseTimes;
    }

    public String getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(String createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
