package cn.artisantc.core.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by xinjie.li on 2016/8/31.
 *
 * @author xinjie.li
 * @since 1.0
 */
@RunWith(PowerMockRunner.class)
public class TestRandomUtil {

    private static final Logger LOG = LoggerFactory.getLogger(TestRandomUtil.class);

    /**
     * 测试“generateSerialNumber”方法。
     *
     * @throws Exception 测试时抛出逇异常
     */
    @Test
    public void testGenerateSerialNumber() throws Exception {
//        String serialNumber = RandomUtil.generateSerialNumber();
        UUID serialNumber = UUID.randomUUID();
        LOG.debug("Generate SerialNumber: {}", serialNumber.toString());

//        Assert.assertTrue(serialNumber.length() == 9);
    }

    /**
     * 测试“generateSerialNumber”方法运行1000000次。
     *
     * @throws Exception 测试时抛出逇异常
     */
    @Test
    public void testGenerateSerialNumber_1000000Times() throws Exception {
        int max = 1000000;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < max; i++) {
            String serialNumber = RandomUtil.generateSerialNumber();
            int length = serialNumber.length();
            if (map.containsKey(length)) {
                map.put(length, map.get(length) + 1);
            } else {
                map.put(length, 1);
            }
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("map.size(): {}", map.size());
        }
        for (Integer i : map.keySet()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("出现 {} 位数字的次数: {}", i, map.get(i));
            }
        }
    }

    /**
     * 测试“generateSMSCaptcha”方法。
     *
     * @throws Exception 测试时抛出逇异常
     */
    @Test
    public void testGenerateSMSCaptcha() throws Exception {
        String captcha = RandomUtil.generateSMSCaptcha();

        if (LOG.isDebugEnabled()) {
            LOG.debug("Generate Captcha: {}", captcha);
        }

        Assert.assertTrue(captcha.length() == 6);
    }

    /**
     * 测试“generateSMSCaptcha”方法运行10000次。
     *
     * @throws Exception 测试时抛出逇异常
     */
    @Test
    public void testGenerateSMSCaptcha_10000Times() throws Exception {
        int max = 10000;
        for (int i = 0; i < max; i++) {
            testGenerateSMSCaptcha();
        }
    }
}
