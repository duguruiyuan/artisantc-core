package cn.artisantc.core.web.rest.v1_0.vo;

/**
 * 获得“用户打赏艺文的支付签名”结果的视图对象。
 * Created by xinjie.li on 2017/2/27.
 *
 * @author xinjie.li
 * @since 2.5
 */
public class PaymentUserRewardView {

    private String sign;// 支付用的签名

    private String orderNumber;// 订单号

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
}
