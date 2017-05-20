package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.ItemOrderReturnAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * “拍品的订单的退货地址”的数据持久化操作。
 * Created by xinjie.li on 2016/10/20.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Repository
public interface ItemOrderReturnAddressRepository extends JpaRepository<ItemOrderReturnAddress, Long>, JpaSpecificationExecutor<ItemOrderReturnAddress> {
}
