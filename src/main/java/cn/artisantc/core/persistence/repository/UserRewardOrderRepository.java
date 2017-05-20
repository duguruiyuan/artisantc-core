package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.UserRewardOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * “用户的打赏订单”的数据持久化操作。
 * Created by xinjie.li on 2017/2/6.
 *
 * @author xinjie.li
 * @since 2.3
 */
@Repository
public interface UserRewardOrderRepository extends JpaRepository<UserRewardOrder, Long>, JpaSpecificationExecutor<UserRewardOrder> {

    /**
     * 获得指定“用户ID”和“订单号”的“打赏支付订单”的信息。
     *
     * @param userId      用户ID
     * @param orderNumber 订单号
     * @return 指定“用户ID”和“订单号”的“打赏支付订单”的信息
     * @since 2.5
     */
    UserRewardOrder findBySender_idAndOrderNumber(long userId, String orderNumber);
}
