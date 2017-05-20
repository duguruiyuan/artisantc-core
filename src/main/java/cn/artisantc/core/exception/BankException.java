package cn.artisantc.core.exception;

/**
 * “银行”相关操作的异常。
 * Created by xinjie.li on 2016/10/9.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class BankException extends RuntimeException {

    public BankException() {
    }

    public BankException(String message) {
        super(message);
    }
}
