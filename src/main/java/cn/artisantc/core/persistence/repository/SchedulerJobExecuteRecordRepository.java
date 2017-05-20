package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.SchedulerJobExecuteRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * “调度任务的执行记录”的数据持久化操作。
 * Created by xinjie.li on 2016/12/8.
 *
 * @author xinjie.li
 * @since 1.1
 */
@Repository(value = "schedulerJobExecuteRecordRepository")
public interface SchedulerJobExecuteRecordRepository extends JpaRepository<SchedulerJobExecuteRecord, Long>, JpaSpecificationExecutor<SchedulerJobExecuteRecord> {
}
