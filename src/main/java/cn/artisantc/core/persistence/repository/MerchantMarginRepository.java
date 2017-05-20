package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.MerchantMargin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * “商家的保证金场”的数据持久化操作。
 * Created by xinjie.li on 2016/10/7.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Repository
public interface MerchantMarginRepository extends JpaRepository<MerchantMargin, Long>, JpaSpecificationExecutor<MerchantMargin> {

    /**
     * 查找指定“买家保证金额度”对应的“保证金场”。
     *
     * @param userMargin 买家保证金额度
     * @return 指定“买家保证金额度”对应的“保证金场”
     */
    MerchantMargin findByUserMargin(BigDecimal userMargin);
}
