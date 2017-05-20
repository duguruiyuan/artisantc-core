package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.ArtMomentReportRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * “艺文(艺术圈)的举报记录”的数据持久化操作。
 * Created by xinjie.li on 2016/9/20.
 *
 * @author xinjie.li
 * @since 1.0
 */
@Repository
public interface ArtMomentReportRecordRepository extends JpaRepository<ArtMomentReportRecord, Long>, JpaSpecificationExecutor<ArtMomentReportRecord> {
}
