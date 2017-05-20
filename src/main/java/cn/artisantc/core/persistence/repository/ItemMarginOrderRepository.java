package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.BaseOrder;
import cn.artisantc.core.persistence.entity.BasePaymentOrder;
import cn.artisantc.core.persistence.entity.ItemMarginOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * “拍品的保证金的支付订单”的数据持久化操作。
 * Created by xinjie.li on 2016/10/6.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Repository
public interface ItemMarginOrderRepository extends JpaRepository<ItemMarginOrder, Long>, JpaSpecificationExecutor<ItemMarginOrder> {

    /**
     * 查找指定用户对指定拍品的指定状态的保证金订单。
     *
     * @param itemId 拍品ID
     * @param userId 用户ID
     * @param status 订单状态
     * @return 指定用户对指定拍品的指定状态的保证金订单
     */
    ItemMarginOrder findByItem_idAndUser_idAndStatus(long itemId, long userId, ItemMarginOrder.Status status);

    /**
     * 查找指定“拍品保证金支付订单的订单号”和“支付渠道”的订单信息。
     *
     * @param itemMarginOrderNumber 拍品保证金支付订单的订单号
     * @param paymentChannel        支付渠道
     * @return 指定“拍品保证金支付订单的订单号”和“支付渠道”的订单信息
     */
    ItemMarginOrder findByOrderNumberAndPaymentChannel(String itemMarginOrderNumber, BaseOrder.PaymentChannel paymentChannel);

    /**
     * 查找指定“拍品ID”的所有“保证金支付订单”。
     *
     * @param itemId 拍品ID
     * @return 指定拍品ID的所有“保证金支付订单”
     */
    List<ItemMarginOrder> findByItem_id(long itemId);

    /**
     * 查找“指定用户”对“指定拍品”“指定类型”的保证金订单。
     *
     * @param itemId   拍品ID
     * @param userId   用户ID
     * @param category 保证金订单的类型
     * @return 指定用户对指定拍品的保证金订单
     */
    ItemMarginOrder findByItem_idAndUser_idAndCategory(long itemId, long userId, BasePaymentOrder.Category category);
}
