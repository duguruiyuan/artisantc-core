package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.RealNameImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * “实名认证的图片”的数据持久化操作。
 * Created by xinjie.li on 2016/10/7.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Repository
public interface RealNameImageRepository extends JpaRepository<RealNameImage, Long>, JpaSpecificationExecutor<RealNameImage> {
}
