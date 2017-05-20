package cn.artisantc.core.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * “调度任务的执行记录”。
 * Created by xinjie.li on 2016/12/8.
 *
 * @author xinjie.li
 * @since 1.1
 */
@Entity
@Table(name = "t_scheduler_job_execute_record")
public class SchedulerJobExecuteRecord extends BaseEntity {

    @Column(name = "scheduler_job_id")
    private long schedulerJobId;// 执行的任务ID

    @Column(name = "scheduler_job_name", length = 60)
    private String schedulerJobName;// 执行的任务名称

    public long getSchedulerJobId() {
        return schedulerJobId;
    }

    public void setSchedulerJobId(long schedulerJobId) {
        this.schedulerJobId = schedulerJobId;
    }

    public String getSchedulerJobName() {
        return schedulerJobName;
    }

    public void setSchedulerJobName(String schedulerJobName) {
        this.schedulerJobName = schedulerJobName;
    }
}
