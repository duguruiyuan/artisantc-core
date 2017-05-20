package cn.artisantc.core.web.rest.v1_0.vo;

/**
 * “拍品支付订单”详情的视图对象。
 * Created by xinjie.li on 2016/10/20.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class ItemPaymentOrderView {

    // 下面是“拍品”信息
    private String title;// 拍品的标题

    private String coverUrl;// 拍品的封面图片的URL地址

    private String bidPrice;// 拍品的成交价

    // 下面是“拍品订单”信息
    private String itemOrderNumber;// 拍品订单的订单号

    private String itemOrderCreateDateTime;// 拍品订单的创建时间

    private String itemOrderDeliveryAddressName;// 拍品订单的收货地址的联系人

    private String itemOrderDeliveryAddressMobile;// 拍品订单的收货地址的联系电话

    private String itemOrderDeliveryAddressDetail;// 拍品订单的收货地址的地址

    // 下面是“支付订单”信息
    private String paymentOrderNumber;// 支付订单的订单号

    private String paymentChannel;// 支付渠道

    private String paymentAmount;// 支付金额

    private String paymentOrderStatus;// 支付订单的状态

    private String paymentOrderCreateDateTime;// 支付订单的创建时间

    private boolean showSynchronizeButton = false;// 是否显示操作的按钮

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(String bidPrice) {
        this.bidPrice = bidPrice;
    }

    public String getItemOrderNumber() {
        return itemOrderNumber;
    }

    public void setItemOrderNumber(String itemOrderNumber) {
        this.itemOrderNumber = itemOrderNumber;
    }

    public String getItemOrderCreateDateTime() {
        return itemOrderCreateDateTime;
    }

    public void setItemOrderCreateDateTime(String itemOrderCreateDateTime) {
        this.itemOrderCreateDateTime = itemOrderCreateDateTime;
    }

    public String getItemOrderDeliveryAddressName() {
        return itemOrderDeliveryAddressName;
    }

    public void setItemOrderDeliveryAddressName(String itemOrderDeliveryAddressName) {
        this.itemOrderDeliveryAddressName = itemOrderDeliveryAddressName;
    }

    public String getItemOrderDeliveryAddressMobile() {
        return itemOrderDeliveryAddressMobile;
    }

    public void setItemOrderDeliveryAddressMobile(String itemOrderDeliveryAddressMobile) {
        this.itemOrderDeliveryAddressMobile = itemOrderDeliveryAddressMobile;
    }

    public String getItemOrderDeliveryAddressDetail() {
        return itemOrderDeliveryAddressDetail;
    }

    public void setItemOrderDeliveryAddressDetail(String itemOrderDeliveryAddressDetail) {
        this.itemOrderDeliveryAddressDetail = itemOrderDeliveryAddressDetail;
    }

    public String getPaymentOrderNumber() {
        return paymentOrderNumber;
    }

    public void setPaymentOrderNumber(String paymentOrderNumber) {
        this.paymentOrderNumber = paymentOrderNumber;
    }

    public String getPaymentChannel() {
        return paymentChannel;
    }

    public void setPaymentChannel(String paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getPaymentOrderStatus() {
        return paymentOrderStatus;
    }

    public void setPaymentOrderStatus(String paymentOrderStatus) {
        this.paymentOrderStatus = paymentOrderStatus;
    }

    public String getPaymentOrderCreateDateTime() {
        return paymentOrderCreateDateTime;
    }

    public void setPaymentOrderCreateDateTime(String paymentOrderCreateDateTime) {
        this.paymentOrderCreateDateTime = paymentOrderCreateDateTime;
    }

    public boolean isShowSynchronizeButton() {
        return showSynchronizeButton;
    }

    public void setShowSynchronizeButton(boolean showSynchronizeButton) {
        this.showSynchronizeButton = showSynchronizeButton;
    }
}
