package cn.artisantc.core.web.rest.v1_0.vo.request;

/**
 * “用户打赏”的请求视图对象。
 * Created by xinjie.li on 2017/2/27.
 *
 * @author xinjie.li
 * @since 2.5
 */
public class PaymentUserRewardRequest {

    private String amount;// 打赏金额

    private String paymentChannel;// 支付渠道

    private String leaveMessage;// 打赏者的留言

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

    public String getLeaveMessage() {
        return leaveMessage;
    }

    public void setLeaveMessage(String leaveMessage) {
        this.leaveMessage = leaveMessage;
    }
}
