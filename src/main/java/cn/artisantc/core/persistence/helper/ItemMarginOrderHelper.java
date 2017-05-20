package cn.artisantc.core.persistence.helper;

import cn.artisantc.core.persistence.entity.BaseOrder;
import cn.artisantc.core.persistence.entity.BasePaymentOrder;
import cn.artisantc.core.persistence.entity.Item;
import cn.artisantc.core.persistence.entity.ItemMarginOrder;
import cn.artisantc.core.persistence.entity.User;
import cn.artisantc.core.util.RandomUtil;
import cn.artisantc.core.util.WordEncoderUtil;

import java.util.Date;

/**
 * “ItemMarginOrder”的帮助类。
 * Created by xinjie.li on 2016/11/14.
 *
 * @author xinjie.li
 * @since 1.0
 */
public class ItemMarginOrderHelper {

    private ItemMarginOrderHelper() {
    }

    /**
     * 创建“指定用户”对“指定拍品”通过“指定的支付渠道”的“保证金”订单。
     *
     * @param user           用户，即买家
     * @param item           拍品，即要缴纳保证金的拍品
     * @param paymentChannel 支付渠道
     * @return 创建好的“缴纳保证金”订单
     */
    public static ItemMarginOrder createPaymentOrder(User user, Item item, BaseOrder.PaymentChannel paymentChannel) {
        assert null != user && null != item && null != paymentChannel;

        ItemMarginOrder newOrder = new ItemMarginOrder();
        newOrder.setUser(user);

        newOrder.setAmount(item.getMargin());
        newOrder.setItem(item);

        Date date = new Date();
        newOrder.setCreateDateTime(date);
        newOrder.setUpdateDateTime(date);
        newOrder.setOrderNumber(WordEncoderUtil.encodeWordWithMD5(System.currentTimeMillis() + user.getMobile() + item.getMargin(), RandomUtil.generateSerialNumber()));// 生成订单号
        newOrder.setPaymentChannel(paymentChannel);
        newOrder.setStatus(BasePaymentOrder.Status.PENDING_PAY);
        newOrder.setCategory(BasePaymentOrder.Category.PAYMENT);
        newOrder.setTitle(MarginHelper.MARGIN_TITLE);

        return newOrder;
    }

    /**
     * 生成一个新的用于“退还保证金”的保证金订单，该新订单是基于买家已经“缴纳的保证金”生成
     *
     * @param oldOrder 买家已经“缴纳的保证金”订单
     * @return 一个新的用于“退还保证金”的保证金订单
     */
    public static ItemMarginOrder createRefundOrder(ItemMarginOrder oldOrder) {
        if (null == oldOrder) {
            return null;
        }

        ItemMarginOrder newOrder = new ItemMarginOrder();
        newOrder.setUser(oldOrder.getUser());

        newOrder.setAmount(oldOrder.getAmount());
        newOrder.setItem(oldOrder.getItem());

        Date date = new Date();
        newOrder.setCreateDateTime(date);
        newOrder.setUpdateDateTime(date);
        newOrder.setOrderNumber(oldOrder.getOrderNumber());// 这里的“订单号”使用原来支付时的订单号！
        newOrder.setPaymentOrderNumber(oldOrder.getPaymentOrderNumber());// 这里的“支付渠道订单号”使用原来支付时的支付渠道订单号！
        newOrder.setPaymentChannel(oldOrder.getPaymentChannel());
        newOrder.setStatus(BasePaymentOrder.Status.PENDING_REFUNDED);
        newOrder.setCategory(BasePaymentOrder.Category.REFUND);// 设置新的订单的类别为“退款”
        newOrder.setTitle(getRefundReason(String.valueOf(oldOrder.getItem().getId())));

        return newOrder;
    }

    /**
     * 获得“退还保证金”的原因。
     *
     * @param itemId 拍品ID，买家缴纳的保证金所对应的拍品
     * @return “退还保证金”的原因
     */
    public static String getRefundReason(String itemId) {
        return "退还参与竞拍(拍品ID:" + itemId + ")的保证金";
    }
}
