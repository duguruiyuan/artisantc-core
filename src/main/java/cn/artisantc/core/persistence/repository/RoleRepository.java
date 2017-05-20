package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * “角色信息”的数据持久化操作。
 * Created by xinjie.li on 2016/8/30.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {

    /**
     * 根据会员角色名称获取角色
     *
     * @param name 角色名称
     * @return 会员角色信息
     */
    List<Role> findByName(String name);
}
