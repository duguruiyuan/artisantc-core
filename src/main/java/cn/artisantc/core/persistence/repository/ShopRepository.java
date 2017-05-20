package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * “店铺”的数据持久化操作。
 * Created by xinjie.li on 2016/9/23.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Repository
public interface ShopRepository extends JpaRepository<Shop, Long>, JpaSpecificationExecutor<Shop> {
    /**
     * 根据给定的“店铺号”和“用户ID”查找对应的店铺信息。
     *
     * @param serialNumber 店铺号
     * @return 对应的店铺信息
     */
    Shop findBySerialNumber(String serialNumber);

    /**
     * 根据给定的“用户ID”查找该用户的店铺信息。
     *
     * @param userId 用户ID
     * @return 该用户的店铺信息
     */
    Shop findByUser_id(long userId);
}
