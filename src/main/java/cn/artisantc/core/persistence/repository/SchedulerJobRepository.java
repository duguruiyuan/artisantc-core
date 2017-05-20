package cn.artisantc.core.persistence.repository;

import cn.artisantc.core.persistence.entity.SchedulerJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * “调度任务”的数据持久化操作。
 * Created by xinjie.li on 2016/12/8.
 *
 * @author xinjie.li
 * @since 1.1
 */
@Repository(value = "schedulerJobRepository")
public interface SchedulerJobRepository extends JpaRepository<SchedulerJob, Long>, JpaSpecificationExecutor<SchedulerJob> {

    /**
     * 根据“任务名称”查找任务信息。
     *
     * @param jobName 任务名称
     * @return “任务名称”对应的任务信息
     */
    SchedulerJob findByJobName(String jobName);
}
