package cn.artisantc.core.exception;

/**
 * “二维码”的异常类。
 * Created by xinjie.li on 2016/12/19.
 *
 * @author xinjie.li
 * @since 1.2
 */
public class QRCodeException extends RuntimeException {

    public QRCodeException(String message) {
        super(message);
    }

    public QRCodeException(String message, Throwable cause) {
        super(message, cause);
    }
}
