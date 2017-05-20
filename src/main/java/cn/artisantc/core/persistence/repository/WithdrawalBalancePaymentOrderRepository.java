package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.BaseOrder;
import cn.artisantc.core.persistence.entity.WithdrawalBalancePaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * “提现的支付订单”的数据持久化操作。
 * Created by xinjie.li on 2016/10/21.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Repository
public interface WithdrawalBalancePaymentOrderRepository extends JpaRepository<WithdrawalBalancePaymentOrder, Long>, JpaSpecificationExecutor<WithdrawalBalancePaymentOrder> {

    /**
     * 查找指定用户的指定订单号的提现的支付订单。
     *
     * @param userId      用户ID
     * @param orderNumber 提现的支付订单的订单号
     * @return 指定用户的指定订单号的提现的支付订单
     */
    WithdrawalBalancePaymentOrder findByUser_idAndOrderNumber(long userId, String orderNumber);

    WithdrawalBalancePaymentOrder findByOrderNumberAndPaymentChannel(String orderNumber, BaseOrder.PaymentChannel aLiPay);
}
