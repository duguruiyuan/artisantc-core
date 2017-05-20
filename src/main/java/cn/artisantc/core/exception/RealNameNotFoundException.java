package cn.artisantc.core.exception;

/**
 * “找不到指定的实名认证信息”的异常。
 * Created by xinjie.li on 2016/10/8.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class RealNameNotFoundException extends BaseNotFoundException {
    public RealNameNotFoundException(String message) {
        super(message);
    }
}
