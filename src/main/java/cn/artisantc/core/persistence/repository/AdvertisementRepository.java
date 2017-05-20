package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * “广告”的数据持久化操作。
 * Created by xinjie.li on 2016/11/4.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long>, JpaSpecificationExecutor<Advertisement> {
}
