package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.WithdrawalMarginPaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * “转出保证金的支付订单”的数据持久化操作。
 * Created by xinjie.li on 2016/11/18.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Repository
public interface WithdrawalMarginPaymentOrderRepository extends JpaRepository<WithdrawalMarginPaymentOrder, Long>, JpaSpecificationExecutor<WithdrawalMarginPaymentOrder> {
}
