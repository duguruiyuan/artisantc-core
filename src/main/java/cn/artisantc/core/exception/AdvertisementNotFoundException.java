package cn.artisantc.core.exception;

/**
 * “找不到相关联的广告”的异常。
 * Created by xinjie.li on 2016/11/15.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class AdvertisementNotFoundException extends BaseNotFoundException {

    public AdvertisementNotFoundException() {
    }

    public AdvertisementNotFoundException(String message) {
        super(message);
    }
}
