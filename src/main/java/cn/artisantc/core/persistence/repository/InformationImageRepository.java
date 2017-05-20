package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.InformationImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * “资讯的图片”的数据持久化操作。
 * Created by xinjie.li on 2016/11/25.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Repository
public interface InformationImageRepository extends JpaRepository<InformationImage, Long>, JpaSpecificationExecutor<InformationImage> {
}
