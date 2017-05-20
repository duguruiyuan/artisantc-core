package cn.artisantc.core.web.rest.v1_0.vo;

/**
 * “用户对艺文进行打赏的支付订单”的视图对象。
 * Created by xinjie.li on 2017/3/1.
 *
 * @author xinjie.li
 * @since 2.5
 */
public class UserRewardOrderView {

    private String status;// 订单状态

    private String orderNumber;// 订单号

    private String amount;// 订单的金额

    private String paymentChannel = "";// 支付渠道

    private String countdown;// 支付倒计时

    private String title;// 订单标题

    private String updateDateTime;// 订单的成交时间

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    public String getPaymentChannel() {
        return paymentChannel;
    }

    public void setPaymentChannel(String paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    public String getCountdown() {
        return countdown;
    }

    public void setCountdown(String countdown) {
        this.countdown = countdown;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(String updateDateTime) {
        this.updateDateTime = updateDateTime;
    }
}
