package cn.artisantc.core.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xinjie.li on 2016/8/31.
 *
 * @author xinjie.li
 * @since 1.0
 */
@RunWith(PowerMockRunner.class)
public class TestWordEncoderUtil {

    private static final Logger LOG = LoggerFactory.getLogger(TestWordEncoderUtil.class);

    /**
     * 测试“encodePasswordWithBCrypt”方法。
     *
     * @throws Exception 测试时抛出逇异常
     */
    @Test
    public void testEncodeWPasswordWithBCrypt() throws Exception {
        String password = "123456";
        String encodeWord = WordEncoderUtil.encodePasswordWithBCrypt(password);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Encode password \"{}\": {}", password, encodeWord);
        }

        Assert.assertTrue(encodeWord.length() == 60);
        Assert.assertTrue(WordEncoderUtil.matchesWithBCrypt(password, encodeWord));
    }

    /**
     * 测试“encodePasswordWithBCrypt”方法运行1000次。
     *
     * @throws Exception 测试时抛出逇异常
     */
    @Test
    public void testEncodeWPasswordWithBCrypt_1000Times() throws Exception {
        int max = 1000;
        for (int i = 0; i < max; i++) {
            testEncodeWPasswordWithBCrypt();
        }
    }
}
