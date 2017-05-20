package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.MerchantImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * “商家认证的图片”的数据持久化操作。
 * Created by xinjie.li on 2016/9/24.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Repository
public interface MerchantImageRepository extends JpaRepository<MerchantImage, Long>, JpaSpecificationExecutor<MerchantImage> {
}
