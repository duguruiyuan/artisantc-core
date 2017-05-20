package cn.artisantc.core.exception;

/**
 * “找不到相关联的资讯”的异常。
 * Created by xinjie.li on 2016/10/20.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class InformationNotFoundException extends BaseNotFoundException {

    public InformationNotFoundException() {
    }

    public InformationNotFoundException(String message) {
        super(message);
    }
}
