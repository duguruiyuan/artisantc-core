package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.ItemOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * “拍品的订单”的数据持久化操作。
 * Created by xinjie.li on 2016/10/12.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Repository
public interface ItemOrderRepository extends JpaRepository<ItemOrder, Long>, JpaSpecificationExecutor<ItemOrder> {

    /**
     * todo:javadoc
     *
     * @param userId   用户ID
     * @param pageable 分页
     * @return
     */
    Page<ItemOrder> findByUser_idOrderByItem_idDesc(long userId, Pageable pageable);

    /**
     * todo:javadoc
     *
     * @param userId 用户ID
     * @return
     */
    long countByUser_idOrderByItem_idDesc(long userId);

    /**
     * todo:javadoc
     *
     * @param orderNumber 订单号
     * @param userId      用户ID
     * @return
     */
    ItemOrder findByOrderNumberAndUser_id(String orderNumber, long userId);

    ItemOrder findByItem_idAndUser_id(long itemId, long userId);

    ItemOrder findByOrderNumber(String orderNumber);
}
