package cn.artisantc.core.web.rest.v1_0.vo.request;

/**
 * “一口价”的请求视图对象。
 * Created by xinjie.li on 2016/11/16.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class ItemFixedRequest {

    private String paymentChannel;// 支付渠道

    public String getPaymentChannel() {
        return paymentChannel;
    }

    public void setPaymentChannel(String paymentChannel) {
        this.paymentChannel = paymentChannel;
    }
}
