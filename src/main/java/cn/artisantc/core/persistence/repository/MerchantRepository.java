package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * “商家认证”的数据持久化操作。
 * Created by xinjie.li on 2016/9/24.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long>, JpaSpecificationExecutor<Merchant> {
    /**
     * 查找指定用户ID的“商家认证信息”。
     *
     * @param userId 用户ID
     * @return 指定用户ID的“商家认证信息”
     */
    Merchant findByUser_id(long userId);

    /**
     * 查找指定用户ID的指定类别(企业商家，个人商家)的“商家认证信息”。
     *
     * @param userId   用户ID
     * @param category 商家类别
     * @return 符合条件的“商家认证信息”
     */
    Merchant findByUser_idAndCategory(long userId, Merchant.Category category);
}
