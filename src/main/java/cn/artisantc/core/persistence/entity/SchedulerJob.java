package cn.artisantc.core.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * “调度任务”的信息。
 * Created by xinjie.li on 2016/12/7.
 *
 * @author xinjie.li
 * @since 1.1
 */
@Entity
@Table(name = "t_scheduler_job", uniqueConstraints = {@UniqueConstraint(name = "UK_SCHEDULER_JOB_JOB_NAME", columnNames = {"job_name"})})
public class SchedulerJob extends BaseEntity {

    @Column(name = "job_name", length = 60)
    private String jobName;// 任务名称

    @Column(name = "job_group", length = 60)
    private String jobGroup;// 任务分组

    @Column(name = "job_class_name", length = 250)
    private String jobClassName;// 该任务的“类名”，即由该类来执行这个任务

    @Column(length = 200)
    private String description;// 任务描述

    @Column(name = "cron_expression", length = 40)
    private String cronExpression;// 触发器的执行规则

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getJobClassName() {
        return jobClassName;
    }

    public void setJobClassName(String jobClassName) {
        this.jobClassName = jobClassName;
    }
}
