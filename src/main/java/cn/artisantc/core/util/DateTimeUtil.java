package cn.artisantc.core.util;

import cn.artisantc.core.web.controller.PaymentConstant;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * “时间处理”工具类。
 * Created by xinjie.li on 2016/9/14.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class DateTimeUtil {

    private DateTimeUtil() {
        // Not allowed use this constructor to get a instance of this class, so
        // declare it to private
    }

    public static final String DATE_FORMAT_ALL = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_FORMAT_YEAR_MONTH_DAY = "yyyy-MM-dd";

    public static final String DATE_FORMAT_MONTH_DAY = "MM-dd";

    /**
     * 将所传时间转换成更易于阅读的描述，规则如下：
     * 1、1分钟以内，显示为“刚刚”。
     * 2、1分钟到1个小时以内，显示为“离当前时间的具体的分钟数”，例如：26分钟前。
     * 3、1到24个小时，显示为“离当前时间的具体的小时数”，例如：12小时前。
     * 4、超过24个小时但未超过3天的，显示为“今天、昨天、前天”。
     * 5、超过3天，但是仍然是当年的，就显示为具体的日期，精确到天即可“MM-dd”。
     * 6、去年和去年以前的，显示为“yyyy-MM-dd”。
     *
     * @param date 要转换的时间
     * @return 转换后的时间描述信息
     */
    public static String getPrettyDescription(final Date date) {
        Assert.notNull(date);// date不能为空

        final long oneMinute = 60000L;// 60秒，1分钟
        final long oneHour = 3600000L;// 3600秒，1个小时
        final long lessThanOneDay = 86400000L;// 24小时
        final long lessThanTwoDays = 172800000L;// 2天
        final long lessThanThreeDays = 259200000L;// 3天

        long currentTimeMillis = System.currentTimeMillis();
        long interval = currentTimeMillis - date.getTime();// 指定时间与当前时间的“时间间隔”

        BigDecimal seconds = new BigDecimal(interval);
        if (interval < oneMinute) {
            // 小于1分钟
            return "刚刚";
        } else if (interval >= oneMinute && interval < oneHour) {
            // 1分钟到1个小时以内，显示为“离当前时间的具体的分钟数”，例如：26分钟前
            BigDecimal minute = seconds.divide(new BigDecimal(oneMinute), BigDecimal.ROUND_DOWN);// 转换成“分钟”
            return minute.toString() + "分钟前";
        } else if (interval >= oneHour && interval < lessThanOneDay) {
            // 1到24个小时，显示为“离当前时间的具体的小时数”，例如：12小时前
            BigDecimal hour = seconds.divide(new BigDecimal(oneHour), BigDecimal.ROUND_DOWN);// 转换成“小时”
            return hour.toString() + "小时前";
        } else if (interval >= lessThanOneDay && interval < lessThanThreeDays) {
            // 超过24个小时但未超过3天的，显示为“昨天几时几分、前天几时几分”
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            String prefix = "前天";
            if (interval < lessThanTwoDays) {
                prefix = "昨天";
            }
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            String hourString = String.valueOf(hour);
            String minuteString = String.valueOf(minute);
            if (hour < 10) {
                hourString = "0" + hour;
            }
            if (minute < 10) {
                minuteString = "0" + minute;
            }
            return prefix + hourString + ":" + minuteString;
        } else {
            // 超过3天的
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(currentTimeMillis);
            int thisYear = calendar.get(Calendar.YEAR);// 今年

            calendar.setTime(date);
            int dateYear = calendar.get(Calendar.YEAR);// 传入的时间所属的年份

            if (thisYear == dateYear) {
                // 今年发布的，就显示为具体的日期，但不包括年份，精确到天即可
                return DateFormatUtils.format(date, DATE_FORMAT_MONTH_DAY);
            } else {
                // 去年以前的，显示“年-月-日”
                return DateFormatUtils.format(date, DATE_FORMAT_YEAR_MONTH_DAY);
            }
        }
    }

    /**
     * 将所传时间转换成离当前时间的倒计时。
     *
     * @param date 要转换的时间
     * @return 计算后的倒计时描述信息
     */
    public static String getCountdownDescription(final Date date) {
        assert null != date;// date不能为空

        long currentTimeMillis = System.currentTimeMillis();
        long interval = date.getTime() - currentTimeMillis;// 指定时间与当前时间的“时间间隔”

        final long oneSecond = 1000L;// 1秒
        final long oneMinute = 60000L;// 60秒，1分钟
        final long oneHour = 3600000L;// 3600秒，1个小时

        BigDecimal seconds = new BigDecimal(interval);// “时间间隔”转换成精度更高的类型方便后面计算

        BigDecimal hour = seconds.divide(new BigDecimal(oneHour), BigDecimal.ROUND_DOWN);// 转换成“小时”
        BigDecimal minute = (seconds.subtract(hour.multiply(new BigDecimal(oneHour)))).divide(new BigDecimal(oneMinute), BigDecimal.ROUND_DOWN);// 转换成“分钟”
        BigDecimal second = (seconds.subtract(hour.multiply(new BigDecimal(oneHour)))).subtract(minute.multiply(new BigDecimal(oneMinute))).divide(new BigDecimal(oneSecond), BigDecimal.ROUND_DOWN);// 转换成“秒”

        StringBuilder sb = new StringBuilder();
        if (hour.longValue() < 10 && hour.longValue() > 0) {
            sb.append("0").append(hour).append(":");
        } else if (hour.longValue() <= 0) {
            sb.append("00").append(":");
        } else if (hour.longValue() >= 10) {
            sb.append(hour).append(":");
        }

        if (minute.longValue() < 10 && minute.longValue() > 0) {
            sb.append("0").append(minute).append(":");
        } else if (minute.longValue() <= 0) {
            sb.append("00").append(":");
        } else if (minute.longValue() >= 10) {
            sb.append(minute).append(":");
        }

        if (second.longValue() < 10 && second.longValue() > 0) {
            sb.append("0").append(second);
        } else if (second.longValue() <= 0) {
            sb.append("00");
        } else if (second.longValue() >= 10) {
            sb.append(second);
        }

        return sb.toString();
    }

    /**
     * 将给定的时间与当前时间进行比较，如果超过了当前的时间则返回true。
     *
     * @param date 给定的时间
     * @return 给定的时间超过了当前的时间则返回true，否则返回false
     */
    public static boolean isOvertime(final Date date) {
        assert null != date;// date不能为空

        long currentTimeMillis = System.currentTimeMillis();
        long interval = date.getTime() - currentTimeMillis;// 指定时间与当前时间的“时间间隔”

        return interval <= 0L;
    }

    /**
     * 指定日期是否在今年。
     *
     * @param date 指定的日期
     * @return 在今年返回true，其他返回false
     */
    public static boolean isThisYear(Date date) {
        if (null == date) {
            return false;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int thisYear = calendar.get(Calendar.YEAR);

        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);

        return year == thisYear;
    }

    /**
     * 计算“支付订单”是否超过了支付的时间限时，如果超过了则返回true。
     *
     * @param date    “支付订单”的创建时间
     * @param timeout 超时时间，{@link PaymentConstant}
     * @return 给定的时间与当前时间进行比较，如果超过了给定的“超时时间”则返回true
     */
    public static boolean isPaymentOrderOvertime(final Date date, final String timeout) {
        assert null != date;// date不能为空

        long currentTimeMillis = System.currentTimeMillis();
        long interval = currentTimeMillis - date.getTime();// 当前时间与支付订单时间的“时间间隔”
        if (PaymentConstant.TIMEOUT_MARGIN_ORDER.equals(timeout)) {
            if (interval >= PaymentConstant.TIMEOUT_MILLIS_MARGIN_ORDER) {
                return true;
            }
        } else if (PaymentConstant.TIMEOUT_ITEM_ORDER.equals(timeout)) {
            if (interval >= PaymentConstant.TIMEOUT_MILLIS_ITEM_ORDER) {
                return true;
            }
        }

        return false;
    }

    /**
     * 判断给定的“时间”是否为“发货超时”。
     *
     * @param date 拍品的买家完成付款的时间，即拍品的“支付订单”的成交时间
     * @return “发货超时”返回true，否则返回false
     */
    public static boolean isPendingDeliveryOvertime(final Date date) {
        assert null != date;// date不能为空

        long currentTimeMillis = System.currentTimeMillis();
        long interval = currentTimeMillis - date.getTime();// 当前时间与指定时间的“时间间隔”
        long timeoutMillisPendingDelivery = 48 * 60 * 60 * 1000;// 48小时

        return interval >= timeoutMillisPendingDelivery;
    }

    /**
     * 判断给定的“时间”是否为“付款超时”。
     *
     * @param date 拍品的买家在中标拍品后，拍品订单的创建时间
     * @return “发货超时”返回true，否则返回false
     */
    public static boolean isPendingPayOvertime(final Date date) {
        assert null != date;// date不能为空

        long currentTimeMillis = System.currentTimeMillis();
        long interval = currentTimeMillis - date.getTime();// 当前时间与指定时间的“时间间隔”
        long timeoutMillisPendingDelivery = 24 * 60 * 60 * 1000;// 24小时

        return interval >= timeoutMillisPendingDelivery;
    }
}
