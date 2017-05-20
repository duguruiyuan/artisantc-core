package cn.artisantc.core.web.rest.v1_0.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * “拍品详情”的视图对象。
 * Created by xinjie.li on 2016/10/4.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class ItemDetailView {

    @JsonProperty(value = "itemId")
    private String id;// 拍品ID

    private String title;// 标题

    private String detail;// 详情

    private String coverUrl;// 封面图片的URL地址

    private List<ItemImageView> images = new ArrayList<>();// 详情中的图片

    private String initialPrice;// 起拍价

    private String raisePrice;// 加价幅度

    private String countdown;// 拍卖开始倒计时

    private String maxBidPrice = "0";// 当前最高竞拍价格

    private boolean freeExpress;// 包邮

    private String expressFee = "0";// 邮费

    private boolean freeReturn;// 包退

    private String margin = "0";// 保证金

    private String referencePrice;// 参考价

    private String fixedPrice;// 一口价

    private List<ItemBidRecordView> bidRecords = new ArrayList<>();// 竞拍排行

    private String status;// 拍品状态

    private String statusCode;// 拍品状态代码

    private String createDateTime;// 拍品的创建时间

    private String startDateTime;// 拍卖开始时间

    private String endDateTime;// 拍卖截至时间

    private String isFixed;// 是否是一口价

    private String serialNumber;// 拍品发布者的用户的匠号

    private String nickname;// 拍品发布者的用户的昵称

    private String avatarUrl = "";// 拍品发布者的头像的缩略图的地址

    private String shopSerialNumber;// 拍品发布者的店铺号

    private String browseTimes = "0";// 拍品的浏览次数

    private String favoritesTimes = "0";// 拍品的收藏次数

    private String isFavorite = "false";// 是否已经收藏

    private boolean showAuditButtons = false;// 是否显示审核操作的按钮

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public List<ItemImageView> getImages() {
        return images;
    }

    public void setImages(List<ItemImageView> images) {
        this.images = images;
    }

    public String getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(String initialPrice) {
        this.initialPrice = initialPrice;
    }

    public String getRaisePrice() {
        return raisePrice;
    }

    public void setRaisePrice(String raisePrice) {
        this.raisePrice = raisePrice;
    }

    public String getCountdown() {
        return countdown;
    }

    public void setCountdown(String countdown) {
        this.countdown = countdown;
    }

    public String getMaxBidPrice() {
        return maxBidPrice;
    }

    public void setMaxBidPrice(String maxBidPrice) {
        this.maxBidPrice = maxBidPrice;
    }

    public boolean isFreeExpress() {
        return freeExpress;
    }

    public void setFreeExpress(boolean freeExpress) {
        this.freeExpress = freeExpress;
    }

    public String getExpressFee() {
        return expressFee;
    }

    public void setExpressFee(String expressFee) {
        this.expressFee = expressFee;
    }

    public boolean isFreeReturn() {
        return freeReturn;
    }

    public void setFreeReturn(boolean freeReturn) {
        this.freeReturn = freeReturn;
    }

    public String getMargin() {
        return margin;
    }

    public void setMargin(String margin) {
        this.margin = margin;
    }

    public String getReferencePrice() {
        return referencePrice;
    }

    public void setReferencePrice(String referencePrice) {
        this.referencePrice = referencePrice;
    }

    public String getFixedPrice() {
        return fixedPrice;
    }

    public void setFixedPrice(String fixedPrice) {
        this.fixedPrice = fixedPrice;
    }

    public List<ItemBidRecordView> getBidRecords() {
        return bidRecords;
    }

    public void setBidRecords(List<ItemBidRecordView> bidRecords) {
        this.bidRecords = bidRecords;
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

    public String getShopSerialNumber() {
        return shopSerialNumber;
    }

    public void setShopSerialNumber(String shopSerialNumber) {
        this.shopSerialNumber = shopSerialNumber;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getBrowseTimes() {
        return browseTimes;
    }

    public void setBrowseTimes(String browseTimes) {
        this.browseTimes = browseTimes;
    }

    public String getFavoritesTimes() {
        return favoritesTimes;
    }

    public void setFavoritesTimes(String favoritesTimes) {
        this.favoritesTimes = favoritesTimes;
    }

    public String getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(String isFavorite) {
        this.isFavorite = isFavorite;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
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

    public boolean isShowAuditButtons() {
        return showAuditButtons;
    }

    public void setShowAuditButtons(boolean showAuditButtons) {
        this.showAuditButtons = showAuditButtons;
    }
}
