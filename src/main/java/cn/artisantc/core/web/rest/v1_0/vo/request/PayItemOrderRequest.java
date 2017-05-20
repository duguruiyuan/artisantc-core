package cn.artisantc.core.web.rest.v1_0.vo.request;

/**
 * “订单进行支付”请求的封装对象。
 * Created by xinjie.li on 2016/10/19.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class PayItemOrderRequest {

    private String paymentChannel;// 支付渠道

    public String getPaymentChannel() {
        return paymentChannel;
    }

    public void setPaymentChannel(String paymentChannel) {
        this.paymentChannel = paymentChannel;
    }
}
