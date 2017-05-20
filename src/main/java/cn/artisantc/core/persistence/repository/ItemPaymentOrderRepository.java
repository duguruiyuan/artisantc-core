package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.BaseOrder;
import cn.artisantc.core.persistence.entity.BasePaymentOrder;
import cn.artisantc.core.persistence.entity.ItemPaymentOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * “拍品订单的支付订单”的数据持久化操作。
 * Created by xinjie.li on 2016/10/20.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Repository
public interface ItemPaymentOrderRepository extends JpaRepository<ItemPaymentOrder, Long>, JpaSpecificationExecutor<ItemPaymentOrder> {

    /**
     * 根据“拍品订单号”和“支付订单的类型”查询“支付订单”。
     *
     * @param itemOrderNumber 拍品订单号
     * @param category        支付订单的类型
     * @return 指定拍品订单的“支付订单”
     */
    ItemPaymentOrder findByItemOrder_orderNumberAndCategory(String itemOrderNumber, BasePaymentOrder.Category category);

    /**
     * todo:javadoc
     *
     * @param orderNumber
     * @param paymentChannel
     * @return
     */
    ItemPaymentOrder findByOrderNumberAndPaymentChannel(String orderNumber, BaseOrder.PaymentChannel paymentChannel);

    Page<ItemPaymentOrder> findByCategoryOrderByCreateDateTimeDescIdDesc(BasePaymentOrder.Category category, Pageable pageable);
}
