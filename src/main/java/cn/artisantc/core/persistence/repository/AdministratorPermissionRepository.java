package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.AdministratorPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * “管理员的权限信息”的数据持久化操作。
 * Created by xinjie.li on 2016/10/5.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Repository
public interface AdministratorPermissionRepository extends JpaRepository<AdministratorPermission, Long>, JpaSpecificationExecutor<AdministratorPermission> {
}
