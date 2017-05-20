package cn.artisantc.core.exception;

/**
 * “找不到相关联的退货地址”的异常。
 * Created by xinjie.li on 2016/10/17.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class ItemReturnAddressNotFoundException extends BaseNotFoundException {

    public ItemReturnAddressNotFoundException() {
    }

    public ItemReturnAddressNotFoundException(String message) {
        super(message);
    }
}
