package cn.artisantc.core.util;

import java.util.regex.Pattern;

/**
 * 正则表达式（Regular expression）验证工具类。
 * Created by xinjie.li on 2016/9/5.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class RegexUtil {

    private RegexUtil() {
        // Not allowed use this constructor to get a instance of this class, so
        // declare it to private
    }

    /**
     * 验证密码规则
     * 长度为8-16位的字母和数字的组合，支持特殊字符：@#$!%&*。
     *
     * @param password 密码
     * @return 匹配规则返回true，不匹配返回false。
     */
    public static boolean validatePassword(String password) {
        return Pattern.compile("[0-9a-zA-Z@#$!%&*]{8,16}").matcher(password).matches();
    }
}
