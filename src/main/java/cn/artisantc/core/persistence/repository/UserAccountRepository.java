package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * “用户的个人账户”的数据持久化操作。
 * Created by xinjie.li on 2017/1/19.
 *
 * @author xinjie.li
 * @since 2.3
 */
@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long>, JpaSpecificationExecutor<UserAccount> {

    /**
     * 查找指定用户的“个人账户”。
     *
     * @param userId 用户ID
     * @return 指定用户的“个人账户”
     */
    UserAccount findByUser_id(long userId);
}
