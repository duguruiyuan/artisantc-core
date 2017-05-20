package cn.artisantc.core.persistence.helper;

import cn.artisantc.core.persistence.entity.MerchantAccountBill;
import cn.artisantc.core.util.DateTimeUtil;

import java.math.BigDecimal;

/**
 * “MerchantAccountBill”的帮助类。
 * Created by xinjie.li on 2016/11/22.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class MerchantAccountBillHelper {

    private MerchantAccountBillHelper() {
    }

    /**
     * 获得包装处理后的“账单信息”，例如："+0.00元 (2016/09/12)"。
     *
     * @param bill 待处理的“账单”
     * @return 包装处理后的“账单信息”
     */
    public static String getLatestAccountBill(MerchantAccountBill bill) {
        if (null == bill) {
            return "0";
        }

        BigDecimal amount = BigDecimal.ZERO;//  订单的金额
        if (MerchantAccountBill.Category.TRANSACTION_SUCCESS == bill.getCategory()) {
            // “交易成功”的账单信息
            amount = bill.getItemOrder().getAmount();
        } else if (MerchantAccountBill.Category.WITHDRAWAL_BALANCE == bill.getCategory()) {
            // “提现”的账单信息
            amount = bill.getWithdrawalBalancePaymentOrder().getAmount();// 提现的金额，包括了手续费
        } else if (MerchantAccountBill.Category.PAY_MARGIN == bill.getCategory()) {
            // “缴纳保证金”的账单信息
            amount = bill.getMerchantMargin().getMerchantMargin();// 缴纳保证金的金额
        } else if (MerchantAccountBill.Category.WITHDRAWAL_MARGIN == bill.getCategory()) {
            // “转出保证金”的账单信息
            amount = bill.getWithdrawalMarginPaymentOrder().getAmount();// 转出保证金的金额，包括了手续费
        }

        StringBuilder sb = new StringBuilder();
        if (amount.compareTo(BigDecimal.ZERO) == -1) {
            sb.append("-");
        } else {
            sb.append("+");
        }
        sb.append(amount.toString());// 金额
        sb.append("元");// 币种
        sb.append(" ");// 增加空格，更美观
        sb.append("(");// 左括号
        sb.append(DateTimeUtil.getPrettyDescription(bill.getCreateDateTime()));// 账单时间
        sb.append(")");// 又括号

        return sb.toString();
    }
}
