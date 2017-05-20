package cn.artisantc.core.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Created by xinjie.li on 2016/9/18.
 *
 * @author xinjie.li
 * @since 1.0
 */
@RunWith(PowerMockRunner.class)
public class TestConverterUtil {

    @Test
    public void testDigitsToStringWithKilo() throws Exception {
        long digits_1 = 999;
        Assert.assertTrue(String.valueOf(digits_1).equals(ConverterUtil.digitsToStringWithKilo(digits_1)));

        long digits_2 = 1000;
        Assert.assertTrue(String.valueOf(ConverterUtil.digitsToStringWithKilo(digits_2)).contains("K"));
    }

    @Test
    public void testGetWrapperBankCardAccount() throws Exception {
        System.out.println(ConverterUtil.getWrapperBankCardAccount("99"));
        System.out.println(ConverterUtil.getWrapperBankCardAccount("5359100818005609"));
        System.out.println(ConverterUtil.getWrapperBankCardAccount("622848040256489001"));
        System.out.println(ConverterUtil.getWrapperBankCardAccount("6222021001116245702"));
    }

    @Test
    public void testGetMerchantEncodeString() throws Exception {
        String createDateTime = "2016-12-07 11:13:29";
        String realName = "123";
        String identityNumber = "yyy";
        long userId = 2;
        String userSerialNumber = "688928223";
        System.out.println(ConverterUtil.getMerchantEncodeString(createDateTime, realName, identityNumber, userId, userSerialNumber));
    }
}
