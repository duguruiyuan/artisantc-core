package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.BaseOrder;
import cn.artisantc.core.persistence.entity.BasePaymentOrder;
import cn.artisantc.core.persistence.entity.MerchantMarginOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * “商家的保证金的支付订单”的数据持久化操作。
 * Created by xinjie.li on 2016/10/6.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Repository
public interface MerchantMarginOrderRepository extends JpaRepository<MerchantMarginOrder, Long>, JpaSpecificationExecutor<MerchantMarginOrder> {

    /**
     * 查找指定用户的指定状态的保证金订单。
     *
     * @param userId 用户ID
     * @param status 订单状态
     * @return 指定用户的指定状态的保证金订单
     */
    List<MerchantMarginOrder> findByUser_idAndStatus(long userId, BasePaymentOrder.Status status);

    /**
     * 查找指定用户的指定订单号的保证金订单。
     *
     * @param userId      用户ID
     * @param orderNumber 订单号
     * @return 指定用户的指定订单号的保证金订单
     */
    MerchantMarginOrder findByUser_idAndOrderNumber(long userId, String orderNumber);

    /**
     * todo:javadoc
     *
     * @param orderNumber
     * @param aLiPay
     * @return
     */
    MerchantMarginOrder findByOrderNumberAndPaymentChannel(String orderNumber, BaseOrder.PaymentChannel aLiPay);
}
