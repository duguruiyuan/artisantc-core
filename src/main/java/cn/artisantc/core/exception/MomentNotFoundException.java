package cn.artisantc.core.exception;

/**
 * “找不到相关联的艺文”的异常。
 * Created by xinjie.li on 2016/9/17.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class MomentNotFoundException extends BaseNotFoundException {

    public MomentNotFoundException(String message) {
        super(message);
    }
}
