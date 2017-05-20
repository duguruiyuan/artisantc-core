package cn.artisantc.core.web.rest.v1_0.vo;

import cn.artisantc.core.util.URLEncodeUtil;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * “拍品”的视图对象。
 * Created by xinjie.li on 2016/9/24.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class ItemView {

    @JsonProperty(value = "itemId")
    private String id;// 拍品ID

    private String title;// 标题

    private String initialPrice;// 起拍价

    private String countdown;// 拍卖开始倒计时

    private String coverUrl;// 封面图片的URL地址

    private String coverWidth;// 封面图片的宽度

    private String coverHeight;// 封面图片的高度

    private String maxBidPrice = "0";// 当前最高竞拍价格

    private String status;// 拍品状态

    private String createDateTime;// 拍品的创建时间

    private String startDateTime;// 拍卖开始时间

    private String endDateTime;// 拍卖截至时间

    private String isFixed;// 是否是“一口价”成交

    private String serialNumber;// 拍品发布者的用户的匠号

    private String nickname;// 拍品发布者的用户的昵称

    private String avatarUrl = "";// 拍品发布者的头像的缩略图的地址

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(String initialPrice) {
        this.initialPrice = initialPrice;
    }

    public String getCountdown() {
        return countdown;
    }

    public void setCountdown(String countdown) {
        this.countdown = countdown;
    }

    public String getCoverUrl() {
        return URLEncodeUtil.replaceSpecialCharacters(coverUrl);
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getMaxBidPrice() {
        return maxBidPrice;
    }

    public void setMaxBidPrice(String maxBidPrice) {
        this.maxBidPrice = maxBidPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(String createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getIsFixed() {
        return isFixed;
    }

    public void setIsFixed(String isFixed) {
        this.isFixed = isFixed;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getCoverWidth() {
        return coverWidth;
    }

    public void setCoverWidth(String coverWidth) {
        this.coverWidth = coverWidth;
    }

    public String getCoverHeight() {
        return coverHeight;
    }

    public void setCoverHeight(String coverHeight) {
        this.coverHeight = coverHeight;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }
}
