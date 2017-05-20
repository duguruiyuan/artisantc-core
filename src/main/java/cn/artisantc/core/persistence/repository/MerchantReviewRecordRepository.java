package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.MerchantReviewRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * “商家认证的审核记录”的数据持久化操作。
 * Created by xinjie.li on 2016/10/4.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Repository
public interface MerchantReviewRecordRepository extends JpaRepository<MerchantReviewRecord, Long>, JpaSpecificationExecutor<MerchantReviewRecord> {
}
