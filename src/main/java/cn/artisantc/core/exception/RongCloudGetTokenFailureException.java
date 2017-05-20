package cn.artisantc.core.exception;

/**
 * “融云”通讯时的异常。
 * Created by xinjie.li on 2016/10/10.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class RongCloudGetTokenFailureException extends RuntimeException {

    public RongCloudGetTokenFailureException() {
    }

    public RongCloudGetTokenFailureException(String message) {
        super(message);
    }
}
