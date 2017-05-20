package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.MerchantMarginAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * “商家的保证金账户”的数据持久化操作。
 * Created by xinjie.li on 2016/10/6.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Repository
public interface MerchantMarginAccountRepository extends JpaRepository<MerchantMarginAccount, Long>, JpaSpecificationExecutor<MerchantMarginAccount> {

    /**
     * 查找指定用户的“商家保证金账户”。
     *
     * @param userId 用户ID
     * @return 指定用户的“商家保证金账户”
     */
    MerchantMarginAccount findByUser_id(long userId);
}
