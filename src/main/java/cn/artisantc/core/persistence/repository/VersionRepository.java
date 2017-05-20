package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * “版本信息”的数据持久化操作。
 * Created by xinjie.li on 2016/12/26.
 *
 * @author xinjie.li
 * @since 2.0
 */
@Repository
public interface VersionRepository extends JpaRepository<Version, String>, JpaSpecificationExecutor<Version> {

    /**
     * 查询指定的“版本号”的数据。
     *
     * @param version 版本号
     * @return 指定的“版本号”的数据
     */
    Version findByVersion(String version);
}
