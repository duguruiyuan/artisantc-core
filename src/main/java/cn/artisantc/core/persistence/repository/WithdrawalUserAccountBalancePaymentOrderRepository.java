package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.WithdrawalUserAccountBalancePaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * “用户的个人账户提现的支付订单”的数据持久化操作。
 * Created by xinjie.li on 2017/2/24.
 *
 * @author xinjie.li
 * @since 2.4
 */
@Repository
public interface WithdrawalUserAccountBalancePaymentOrderRepository extends JpaRepository<WithdrawalUserAccountBalancePaymentOrder, Long>, JpaSpecificationExecutor<WithdrawalUserAccountBalancePaymentOrder> {
}
