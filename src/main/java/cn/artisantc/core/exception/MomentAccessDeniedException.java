package cn.artisantc.core.exception;

/**
 * “艺文访问拒绝”的异常。
 * Created by xinjie.li on 2016/9/21.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class MomentAccessDeniedException extends RuntimeException {

    public MomentAccessDeniedException(String message) {
        super(message);
    }
}
