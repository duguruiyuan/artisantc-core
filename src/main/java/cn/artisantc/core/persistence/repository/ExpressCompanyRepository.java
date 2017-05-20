package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.ExpressCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * “快递公司”的数据持久化操作。
 * Created by xinjie.li on 2016/10/19.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Repository
public interface ExpressCompanyRepository extends JpaRepository<ExpressCompany, String>, JpaSpecificationExecutor<ExpressCompany> {
}
