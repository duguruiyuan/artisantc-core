package cn.artisantc.core.web.rest.v1_0.vo.request;

/**
 * “缴纳保证金”的请求视图对象。
 * Created by xinjie.li on 2016/10/6.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class PaymentMarginRequest {

    private String marginId;// 保证金场次的ID

    private String paymentChannel;// 支付渠道

    public String getPaymentChannel() {
        return paymentChannel;
    }

    public void setPaymentChannel(String paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    public String getMarginId() {
        return marginId;
    }

    public void setMarginId(String marginId) {
        this.marginId = marginId;
    }
}
