package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.ItemDeliveryAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * “收货地址”的数据持久化操作。
 * Created by xinjie.li on 2016/10/17.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Repository
public interface ItemDeliveryAddressRepository extends JpaRepository<ItemDeliveryAddress, Long>, JpaSpecificationExecutor<ItemDeliveryAddress> {

    /**
     * 获得指定用户的“收货地址”列表。
     *
     * @param userId 用户ID
     * @return 指定用户的“收货地址”列表
     */
    List<ItemDeliveryAddress> findByUser_idOrderByIsDefaultDescIdDesc(long userId);

    /**
     * 计算指定用户的默认“收货地址”的数量。
     *
     * @param userId 用户ID
     * @return 指定用户的默认“收货地址”的数量
     */
    long countByUser_idAndIsDefaultIsTrue(long userId);

    /**
     * 获得指定用户的默认“收货地址”。
     * @param userId 用户ID
     * @return 指定用户的默认“收货地址”
     */
    ItemDeliveryAddress findByUser_idAndIsDefaultIsTrue(long userId);

    /**
     * 获得指定用户和地址ID的“收货地址”。
     *
     * @param userId 用户ID
     * @param addressId 地址ID
     * @return 指定用户和地址ID的“收货地址”
     */
    ItemDeliveryAddress findByUser_idAndId(long userId, long addressId);
}
