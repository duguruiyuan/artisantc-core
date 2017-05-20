package cn.artisantc.core.exception;

/**
 * “上传广告图片错误”的异常。
 * Created by xinjie.li on 2016/11/16.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class UploadAdvertisementImageFailureException extends RuntimeException {

    public UploadAdvertisementImageFailureException() {
    }

    public UploadAdvertisementImageFailureException(String message) {
        super(message);
    }
}
