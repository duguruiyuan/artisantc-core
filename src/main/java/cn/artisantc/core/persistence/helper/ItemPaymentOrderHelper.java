package cn.artisantc.core.persistence.helper;

import cn.artisantc.core.persistence.entity.BasePaymentOrder;
import cn.artisantc.core.persistence.entity.ItemPaymentOrder;

import java.util.Date;

/**
 * “ItemPaymentOrderHelper”的帮助类。
 * Created by xinjie.li on 2016/11/14.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class ItemPaymentOrderHelper {

    private ItemPaymentOrderHelper() {
    }

    /**
     * 生成一个新的用于“退款”的支付订单，该新订单是基于买家已经“付款”生成
     *
     * @param oldOrder 买家已经“付款”的订单
     * @return 一个新的用于“退款”的支付订单
     */
    public static ItemPaymentOrder createRefundOrder(ItemPaymentOrder oldOrder) {
        if (null == oldOrder) {
            return null;
        }

        ItemPaymentOrder newOrder = new ItemPaymentOrder();
        newOrder.setItemOrder(oldOrder.getItemOrder());
        newOrder.setCategory(oldOrder.getCategory());
        newOrder.setUser(oldOrder.getUser());
        newOrder.setOrderNumber(oldOrder.getOrderNumber());// 这里的“订单号”使用原来支付时的订单号！
        newOrder.setPaymentOrderNumber(oldOrder.getPaymentOrderNumber());// 这里的“支付渠道订单号”使用原来支付时的支付渠道订单号！
        newOrder.setStatus(BasePaymentOrder.Status.PENDING_REFUNDED);
        newOrder.setCategory(BasePaymentOrder.Category.REFUND);
        newOrder.setAmount(oldOrder.getAmount());
        newOrder.setPaymentChannel(oldOrder.getPaymentChannel());
        newOrder.setTitle(getRefundReason(oldOrder.getItemOrder().getOrderNumber()));

        Date date = new Date();
        newOrder.setCreateDateTime(date);
        newOrder.setUpdateDateTime(date);

        return newOrder;
    }

    /**
     * 获得“退款”原因。
     *
     * @param itemOrderNumber 拍品订单的订单号
     * @return “退款”原因
     */
    public static String getRefundReason(String itemOrderNumber) {
        return "退还购买(拍品订单:" + itemOrderNumber + ")金额";
    }
}
