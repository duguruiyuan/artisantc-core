package cn.artisantc.core.service.payment;

import cn.artisantc.core.persistence.entity.BaseOrder;
import cn.artisantc.core.persistence.entity.ItemMarginOrder;
import cn.artisantc.core.persistence.entity.ItemPaymentOrder;
import cn.artisantc.core.persistence.entity.MerchantMarginOrder;
import cn.artisantc.core.persistence.entity.UserRewardOrder;
import cn.artisantc.core.web.rest.v1_0.vo.ItemPaymentOrderView;
import cn.artisantc.core.web.rest.v1_0.vo.PaymentUserRewardView;
import cn.artisantc.core.web.rest.v1_0.vo.UserRewardOrderView;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.ItemMarginOrderViewPaginationList;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.ItemPaymentOrderViewPaginationList;
import cn.artisantc.core.web.rest.v1_0.vo.pagination.MerchantMarginOrderViewPaginationList;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 支持“支付”操作的服务接口。
 * Created by xinjie.li on 2016/10/7.
 *
 * @author xinjie.li
 * @since 1.0
 */
public interface PaymentService {

    /**
     * todo：javadoc
     *
     * @param itemOrderNumber
     * @param paymentChannel
     * @return 生成的支付订单，若生成失败则返回null
     */
    String payItemOrder(String itemOrderNumber, String paymentChannel);

    /**
     * todo：javadoc
     *
     * @param itemOrderNumber    拍品订单的订单号
     * @param paymentOrderNumber 支付渠道返回的订单号
     * @return
     */
    ItemPaymentOrderView getItemPayOrderByItemOrderNumber(String itemOrderNumber, String paymentOrderNumber);

    /**
     * todo：javadoc
     *
     * @param itemOrderNumber 拍品订单的订单号
     * @return
     */
    ItemPaymentOrderView getItemPayOrderByItemOrderNumber(String itemOrderNumber);

    /**
     * 获得指定“商家保证金支付订单”的“支付宝订单”信息。
     *
     * @param merchantMarginOrder 支付订单所对应的“商家保证金支付订单”
     */
    MerchantMarginOrder getALiPayOrder(MerchantMarginOrder merchantMarginOrder);

    /**
     * 获得指定“买家保证金支付订单”的“支付宝订单”信息。
     *
     * @param itemMarginOrder 支付订单所对应的“买家保证金支付订单”
     */
    ItemMarginOrder getALiPayOrder(ItemMarginOrder itemMarginOrder);

    /**
     * 获得指定“买家拍品支付订单”的“支付宝订单”信息。
     *
     * @param itemPaymentOrder 支付订单所对应的“买家拍品支付订单”
     */
    ItemPaymentOrder getALiPayOrder(ItemPaymentOrder itemPaymentOrder);

    /**
     * todo:javadoc
     *
     * @return
     */
    boolean getALiPayNotifyCallback(Map<String, String[]> parameterMap);

    /**
     * todo:javadoc
     *
     * @param order
     * @return
     */
    MerchantMarginOrder getWeiXinPayOrder(MerchantMarginOrder order);

    /**
     * todo:javadoc
     *
     * @param order
     * @return
     */
    ItemMarginOrder getWeiXinPayOrder(ItemMarginOrder order);

    /**
     * 获得指定“买家拍品支付订单”的“支付宝订单”信息。
     *
     * @param itemPaymentOrder 支付订单所对应的“买家拍品支付订单”
     */
    ItemPaymentOrder getWeiXinPayOrder(ItemPaymentOrder itemPaymentOrder);

    /**
     * 获得对指定“订单号”和“订单金额”的用于支付用的签名，用于APP端使用，完成支付交易。
     *
     * @param orderNumber    订单号
     * @param amount         订单金额
     * @param channel        支付渠道
     * @param timeoutExpress 支付订单的最晚付款时间，逾期将关闭交易。
     * @param subject        订单标题
     * @return 支付用的签名
     */
    String getPaySign(String orderNumber, BigDecimal amount, BaseOrder.PaymentChannel channel, String timeoutExpress, String subject);

    /**
     * “退还买家缴纳的保证金”。
     *
     * @param marginOrder 待退还的买家缴纳的保证金的支付订单
     */
    void refundItemMarginOrder(ItemMarginOrder marginOrder);

    /**
     * “退还买家购买拍品的支付钱款”。
     *
     * @param itemPaymentOrder 待退还的买家购买拍品的支付钱款
     */
    void refundItemPaymentOrder(ItemPaymentOrder itemPaymentOrder);

    /**
     * 获得“商家保证金的支付签名”。
     *
     * @param orderNumber    商家保证金的支付订单的订单号
     * @param paymentChannel 支付渠道
     * @return 对应“支付渠道”的“商家保证金的支付签名”
     */
    String getPaySignForMerchantMargin(String orderNumber, String paymentChannel);

    /**
     * 获得“拍品订单的支付订单”列表。
     *
     * @param page 分页
     * @return “拍品订单的支付订单”列表
     * @since 1.1
     */
    ItemPaymentOrderViewPaginationList findItemPaymentOrders(int page);

    /**
     * 获得“拍品退款订单”列表。
     *
     * @param page 分页
     * @return “拍品退款订单”列表
     * @since 1.1
     */
    ItemPaymentOrderViewPaginationList findItemRefundOrders(int page);

    /**
     * 获得“拍品保证金的支付订单列表”列表。
     *
     * @param page 分页
     * @return “拍品保证金的支付订单列表”列表
     * @since 1.1
     */
    ItemMarginOrderViewPaginationList findItemMarginOrders(int page);

    /**
     * 获得“拍品保证金的退款订单列表”列表。
     *
     * @param page 分页
     * @return “拍品保证金的退款订单列表”列表
     * @since 1.1
     */
    ItemMarginOrderViewPaginationList findItemMarginRefundOrders(Integer page);

    /**
     * 获得“商家保证金的支付订单列表”列表。
     *
     * @param page 分页
     * @return “商家保证金的支付订单列表”列表
     * @since 1.1
     */
    MerchantMarginOrderViewPaginationList findMerchantMarginOrders(int page);

    /**
     * 获得“用户打赏艺文的支付签名”，并生成订单。
     *
     * @param momentId       艺文ID
     * @param amount         打赏金额
     * @param paymentChannel 支付渠道
     * @param leaveMessage   打赏者的留言
     * @return “用户打赏艺文的支付签名”
     * @since 2.5
     */
    PaymentUserRewardView getPaySignForUserReward(String momentId, String amount, String paymentChannel, String leaveMessage);

    /**
     * 获得指定“用户打赏的支付订单”的“支付宝订单”信息。
     *
     * @param order 用户打赏的支付订单
     * @return 指定“用户打赏的支付订单”的“支付宝订单”信息
     */
    UserRewardOrder getALiPayOrder(UserRewardOrder order);

    /**
     * 获得指定“用户打赏的支付订单”的“微信订单”信息。
     *
     * @param order 用户打赏的支付订单
     * @return 定“用户打赏的支付订单”的“微信订单”信息
     */
    UserRewardOrder getWeiXinPayOrder(UserRewardOrder order);


    /**
     * 获得指定“订单号”的“打赏支付订单”信息，并与第三方支付进行通讯用于同步其支付数据。
     *
     * @param orderNumber        “订单号”
     * @param paymentOrderNumber 支付渠道返回的订单号
     * @return 指定“订单号”的“打赏支付订单”信息
     * @since 2.5
     */
    UserRewardOrderView getUserRewardOrderByOrderNumber(String orderNumber, String paymentOrderNumber);
}
