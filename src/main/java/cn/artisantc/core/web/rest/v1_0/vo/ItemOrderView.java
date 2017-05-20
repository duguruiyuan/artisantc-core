package cn.artisantc.core.web.rest.v1_0.vo;

/**
 * “拍品订单”的视图对象。
 * Created by xinjie.li on 2016/10/12.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class ItemOrderView {

    private String orderNumber;// 订单号

    private String amount = "0";// 订单的金额

    private String createDateTime;// 订单的创建时间

    private String status;// 订单状态

    private String statusCode;// 订单状态代码

    private String result = "";// 订单结果

    private String resultCode = "";// 订单结果代码

    private String title;// 拍品的标题

    private String initialPrice = "0";// 拍品的起拍价

    private String coverUrl;// 拍品的封面图片的URL地址

    private String isFixed;// 拍品是否是“一口价”成交

    private String itemId;// 拍品ID

    private String serialNumber;// 用户的匠号，对于“买家的订单”来说，这个用户匠号就是“卖家的用户匠号”，反之亦然

    private String nickname;// 用户的昵称，对于“买家的订单”来说，这个用户昵称就是“卖家的用户昵称”，反之亦然

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(String createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getIsFixed() {
        return isFixed;
    }

    public void setIsFixed(String isFixed) {
        this.isFixed = isFixed;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
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

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }
}
