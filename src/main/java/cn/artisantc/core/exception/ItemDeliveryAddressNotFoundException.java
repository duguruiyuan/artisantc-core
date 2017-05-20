package cn.artisantc.core.exception;

/**
 * “找不到相关联的收货地址”的异常。
 * Created by xinjie.li on 2016/10/17.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class ItemDeliveryAddressNotFoundException extends BaseNotFoundException {

    public ItemDeliveryAddressNotFoundException() {
    }

    public ItemDeliveryAddressNotFoundException(String message) {
        super(message);
    }
}
