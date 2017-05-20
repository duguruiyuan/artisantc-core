package cn.artisantc.core.persistence.helper;

import cn.artisantc.core.persistence.entity.ItemOrder;

import java.math.BigDecimal;

/**
 * “ItemOrder”的帮助类。
 * Created by xinjie.li on 2016/11/18.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class ItemOrderHelper {

    private ItemOrderHelper() {
    }

    /**
     * 获得“拍品订单”的实际支付金额。
     *
     * @param itemOrder 拍品订单
     * @return “拍品订单”的实际支付金额
     */
    public static BigDecimal getPayAmount(ItemOrder itemOrder) {
        if (null == itemOrder) {
            return BigDecimal.ZERO;
        }
        BigDecimal amount = itemOrder.getAmount();// 订单金额 = 拍品订单金额 + 拍品邮费
        if (!itemOrder.getItem().isFreeExpress() && itemOrder.getItem().getExpressFee().compareTo(BigDecimal.ZERO) == 1) {
            // 不包邮，则订单的支付金额应当包含邮费：订单金额 = 拍品订单金额 + 拍品邮费
            amount = amount.add(itemOrder.getItem().getExpressFee());
        }
        return amount;
    }
}
