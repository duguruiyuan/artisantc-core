package cn.artisantc.core.exception;

/**
 * “找不到相关联的拍品订单”的异常。
 * Created by xinjie.li on 2016/10/18.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class ItemOrderNotFoundException extends BaseNotFoundException {

    public ItemOrderNotFoundException() {
    }

    public ItemOrderNotFoundException(String message) {
        super(message);
    }
}
