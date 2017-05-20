package cn.artisantc.core.web.controller;

/**
 * “支付”相关的常量集合。
 * Created by xinjie.li on 2016/10/31.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class PaymentConstant {

    private PaymentConstant() {
    }

    /**
     * “保证金支付订单”的最晚付款时间：15分钟，包括“商家的保证金支付订单”和“买家的保证金支付订单”，该参数用于向第三方支付发起请求时使用。
     */
    public static final String TIMEOUT_MARGIN_ORDER = "15m";// 15分钟

    /**
     * “买家的拍品订单”的最晚付款时间：24小时，该参数用于向第三方支付发起请求时使用。
     */
    public static final String TIMEOUT_ITEM_ORDER = "24h";// 24小时

    /**
     * “用户打赏的支付订单”的最晚付款时间：15分钟，该参数用于向第三方支付发起请求时使用。
     */
    public static final String TIMEOUT_USER_REWARD_ORDER = "15m";// 15分钟

    /**
     * “保证金支付订单”的超时时间。
     */
    public static final long TIMEOUT_MILLIS_MARGIN_ORDER = 15 * 60 * 1000;// 15分钟

    /**
     * “买家的拍品订单”的超时时间。
     */
    public static final long TIMEOUT_MILLIS_ITEM_ORDER = 24 * 60 * 60 * 1000;// 24小时

    /**
     * “用户打赏的支付订单”的超时时间。
     */
    public static final long TIMEOUT_MILLIS_USER_REWARD_ORDER = 15 * 60 * 1000;// 15分钟
}
