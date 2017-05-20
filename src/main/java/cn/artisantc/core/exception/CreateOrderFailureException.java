package cn.artisantc.core.exception;

/**
 * “创建订单失败”的异常。
 * Created by xinjie.li on 2016/10/6.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class CreateOrderFailureException extends RuntimeException {

    public CreateOrderFailureException() {
    }

    public CreateOrderFailureException(String message) {
        super(message);
    }
}
