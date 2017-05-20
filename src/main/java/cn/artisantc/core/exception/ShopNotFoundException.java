package cn.artisantc.core.exception;

/**
 * “找不到相关联的店铺”的异常。
 * Created by xinjie.li on 2016/9/24.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class ShopNotFoundException extends BaseNotFoundException {

    public ShopNotFoundException(String message) {
        super(message);
    }
}
