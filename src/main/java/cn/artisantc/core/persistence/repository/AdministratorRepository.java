package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * “管理员”的数据持久化操作。
 * Created by xinjie.li on 2016/10/4.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Long>, JpaSpecificationExecutor<Administrator> {

    /**
     * 根据指定的“用户名”查找用户。
     *
     * @param username 用户名
     * @return 指定手机号所对应的用户
     */
    Administrator findByUsername(String username);
}
