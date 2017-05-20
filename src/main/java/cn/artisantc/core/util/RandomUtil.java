package cn.artisantc.core.util;

import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 随机数生成工具类。
 * Created by xinjie.li on 2016/8/31.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class RandomUtil {

    private RandomUtil() {
        // Not allowed use this constructor to get a instance of this class, so
        // declare it to private
    }

    /**
     * 随机生成一个9位的数字。
     *
     * @return 9位的随机数字
     */
    public static String generateSerialNumber() {
        String serialNumber = String.valueOf(RandomUtils.nextLong(100000000, 999999999L));

        List<String> reservedNumbers = new ArrayList<>();// 保留数字
        reservedNumbers.add("100000000");
        reservedNumbers.add("200000000");
        reservedNumbers.add("300000000");
        reservedNumbers.add("400000000");
        reservedNumbers.add("500000000");
        reservedNumbers.add("600000000");
        reservedNumbers.add("700000000");
        reservedNumbers.add("800000000");
        reservedNumbers.add("900000000");

        reservedNumbers.add("111111111");
        reservedNumbers.add("222222222");
        reservedNumbers.add("333333333");
        reservedNumbers.add("444444444");
        reservedNumbers.add("555555555");
        reservedNumbers.add("666666666");
        reservedNumbers.add("777777777");
        reservedNumbers.add("888888888");
        reservedNumbers.add("999999999");

        reservedNumbers.add("123456789");
        reservedNumbers.add("234567891");
        reservedNumbers.add("345678912");
        reservedNumbers.add("456789123");
        reservedNumbers.add("567891234");
        reservedNumbers.add("678912345");
        reservedNumbers.add("789123456");
        reservedNumbers.add("891234567");
        reservedNumbers.add("912345678");
        reservedNumbers.add("987654321");

        // todo: 不能出现连续3个相同的数字

        while (reservedNumbers.contains(serialNumber)) {// 如果生成了保留数字，则重新生成
            serialNumber = String.valueOf(RandomUtils.nextLong(100000000, 999999999L));
        }

        return serialNumber;
    }

    /**
     * 随机生成一个6位的数字，用做短信验证码。
     *
     * @return 6位的随机数字
     */
    public static String generateSMSCaptcha() {
        return String.valueOf(RandomUtils.nextInt(100000, 999999));
    }
}
