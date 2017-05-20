package cn.artisantc.core.web.rest.v1_0.vo;

/**
 * “拍品的保证金的支付订单”详情的视图对象。
 * Created by xinjie.li on 2016/10/20.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class ItemMarginOrderView {

    private String name;// 支付订单的名称

    private String orderNumber;// 支付订单的订单号

    private String createDateTime;// 支付订单的创建时间

    private String status;// 支付订单的状态

    private String paymentChannel;// 支付订单使用的支付渠道

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
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

    public String getPaymentChannel() {
        return paymentChannel;
    }

    public void setPaymentChannel(String paymentChannel) {
        this.paymentChannel = paymentChannel;
    }
}
