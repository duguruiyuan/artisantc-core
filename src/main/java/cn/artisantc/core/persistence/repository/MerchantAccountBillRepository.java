package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.MerchantAccountBill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * “商家账户的账单”的数据持久化操作。
 * Created by xinjie.li on 2016/10/21.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Repository
public interface MerchantAccountBillRepository extends JpaRepository<MerchantAccountBill, Long>, JpaSpecificationExecutor<MerchantAccountBill> {

    /**
     * 指定用户的账单分页列表。
     *
     * @param userId   用户ID
     * @param pageable 分页
     * @return 指定用户的账单分页列表
     */
    Page<MerchantAccountBill> findByUser_idOrderByCreateDateTimeDescIdDesc(long userId, Pageable pageable);

    /**
     * 查找指定用户的指定账单ID的账单详情。
     *
     * @param userId 用户ID
     * @param billId 账单ID
     * @return 指定用户的指定账单ID的账单详情
     */
    MerchantAccountBill findByUser_idAndId(long userId, long billId);

    /**
     * 指定用户的“最新”的一个账单。
     *
     * @param userI 用户ID
     * @return 指定用户的“最新”的一个账单
     */
    MerchantAccountBill findFirstByUser_idOrderByCreateDateTimeDescIdDesc(long userI);

    /**
     * 获得根据指定的“账单ID”和“账单类型”的账单信息。
     *
     * @param category 账单类型
     * @param billId   账单ID
     * @return 根据指定的“账单ID”和“账单类型”的账单信息
     */
    MerchantAccountBill findByCategoryAndId(MerchantAccountBill.Category category, long billId);
}
