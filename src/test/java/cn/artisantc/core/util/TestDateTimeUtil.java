package cn.artisantc.core.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by xinjie.li on 2016/9/24.
 *
 * @author xinjie.li
 * @since 1.0
 */
@RunWith(PowerMockRunner.class)
public class TestDateTimeUtil {

    private static final Logger LOG = LoggerFactory.getLogger(TestDateTimeUtil.class);

    /**
     * 测试“generateSerialNumber”方法。
     *
     * @throws Exception 测试时抛出逇异常
     */
    @Test
    public void testGetCountdownDescription() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, 48);// 48小时以后
        String countdown = DateTimeUtil.getCountdownDescription(calendar.getTime());
        LOG.debug("48小时倒计时: {}", countdown);

        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, 9);// 9小时以后
        countdown = DateTimeUtil.getCountdownDescription(calendar.getTime());
        LOG.debug("9小时倒计时: {}", countdown);

        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, 55);// 55分钟以后
        countdown = DateTimeUtil.getCountdownDescription(calendar.getTime());
        LOG.debug("55分钟倒计时: {}", countdown);

        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, 9);// 9分钟以后
        countdown = DateTimeUtil.getCountdownDescription(calendar.getTime());
        LOG.debug("9分钟倒计时: {}", countdown);

        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, 20);// 20秒以后
        countdown = DateTimeUtil.getCountdownDescription(calendar.getTime());
        LOG.debug("20秒倒计时: {}", countdown);

        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, 9);// 9秒以后
        countdown = DateTimeUtil.getCountdownDescription(calendar.getTime());
        LOG.debug("9秒倒计时: {}", countdown);
    }
}
