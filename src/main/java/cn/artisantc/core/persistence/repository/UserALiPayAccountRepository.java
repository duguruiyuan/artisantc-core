package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.UserALiPayAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * “用户的支付宝账户”的数据持久化操作。
 * Created by xinjie.li on 2017/2/24.
 *
 * @author xinjie.li
 * @since 2.4
 */
@Repository
public interface UserALiPayAccountRepository extends JpaRepository<UserALiPayAccount, Long>, JpaSpecificationExecutor<UserALiPayAccount> {

    /**
     * 获得指定“用户ID”是否拥有指定的“支付宝帐号”的信息。
     *
     * @param userId            用户ID
     * @param userALiPayAccount 支付宝帐号
     * @return 指定“用户ID”是否拥有指定的“支付宝帐号”的信息
     */
    UserALiPayAccount findByUser_idAndAccount(long userId, String userALiPayAccount);

    /**
     * 计算指定“用户ID”的默认使用的支付宝账户的数量。
     *
     * @param userId 用户ID
     * @return 计算指定“用户ID”的默认使用的支付宝账户的数量
     */
    long countByUser_idAndIsDefault(long userId, boolean isDefault);
}
