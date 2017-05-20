package cn.artisantc.core.persistence.helper;

import java.math.BigDecimal;

/**
 * todo:javadoc
 * Created by xinjie.li on 2016/11/21.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class BaseWithdrawalPaymentOrder {

    private BaseWithdrawalPaymentOrder() {
    }

    public static String getWithdrawalBalanceTitle() {
        return "余额提现";
    }

    public static String getWithdrawalMarginTitle() {
        return "转出保证金";
    }

    public static String getUserRewardTitle() {
        return "收到薪赏";
    }

    /**
     * 用于计算的“提现手续费的费率”，0.001 = 0.1%。
     */
    public static final BigDecimal WITHDRAWAL_CHARGE_RATE = new BigDecimal("0.001");

    /**
     * 用于显示的“提现手续费的费率”，0.1%。
     */
    public static final String WITHDRAWAL_CHARGE_RATE_TEXT = "0.1%";

    /**
     * 提现手续费的最低值，0.10元。
     */
    public static final BigDecimal MIN_WITHDRAWAL_CHARGE = new BigDecimal("0.10");
}
