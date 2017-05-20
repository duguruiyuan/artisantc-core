package cn.artisantc.core.exception;

/**
 * “找不到相关联的支付订单”的异常。
 * Created by xinjie.li on 2016/10/21.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class PaymentOrderNotFoundException extends BaseNotFoundException {

    public PaymentOrderNotFoundException() {
    }

    public PaymentOrderNotFoundException(String message) {
        super(message);
    }
}
