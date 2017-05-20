package cn.artisantc.core.exception;

/**
 * “找不到相关联的商铺”的异常。
 * Created by xinjie.li on 2016/9/28.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class MerchantNotFoundException extends BaseNotFoundException {
    public MerchantNotFoundException(String message) {
        super(message);
    }
}
