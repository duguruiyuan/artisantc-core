package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.Information;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * “资讯”的数据持久化操作。
 * Created by xinjie.li on 2016/10/20.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Repository
public interface InformationRepository extends JpaRepository<Information, Long>, JpaSpecificationExecutor<Information> {
}
