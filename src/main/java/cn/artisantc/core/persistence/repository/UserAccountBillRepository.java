package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.UserAccountBill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * “用户的个人账户的账单”的数据持久化操作。
 * Created by xinjie.li on 2017/2/9.
 *
 * @author xinjie.li
 * @since 2.4
 */
@Repository
public interface UserAccountBillRepository extends JpaRepository<UserAccountBill, Long>, JpaSpecificationExecutor<UserAccountBill> {

    /**
     * 指定用户的账单分页列表。
     *
     * @param userId   用户ID
     * @param pageable 分页
     * @return 指定用户的账单分页列表
     */
    Page<UserAccountBill> findByUser_idOrderByCreateDateTimeDescIdDesc(long userId, Pageable pageable);

    /**
     * 查找指定用户的指定账单ID的账单详情。
     *
     * @param userId 用户ID
     * @param billId 账单ID
     * @return 指定用户的指定账单ID的账单详情
     */
    UserAccountBill findByUser_idAndId(long userId, long billId);
}
