package cn.artisantc.core.exception;

/**
 * “找不到相关联的拍品”的异常。
 * Created by xinjie.li on 2016/10/4.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class ItemNotFoundException extends BaseNotFoundException {
    public ItemNotFoundException(String message) {
        super(message);
    }
}
