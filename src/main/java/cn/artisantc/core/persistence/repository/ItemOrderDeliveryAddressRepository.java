package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.ItemOrderDeliveryAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * “拍品的订单的收货地址”的数据持久化操作。
 * Created by xinjie.li on 2016/10/20.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Repository
public interface ItemOrderDeliveryAddressRepository extends JpaRepository<ItemOrderDeliveryAddress, Long>, JpaSpecificationExecutor<ItemOrderDeliveryAddress> {
}
