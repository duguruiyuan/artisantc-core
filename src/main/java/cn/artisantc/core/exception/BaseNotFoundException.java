package cn.artisantc.core.exception;

/**
 * “找不到相关联的资源”的异常的基类。
 * Created by xinjie.li on 2016/9/22.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class BaseNotFoundException extends RuntimeException {

    public BaseNotFoundException() {
    }

    public BaseNotFoundException(String message) {
        super(message);
    }
}
