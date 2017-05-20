package cn.artisantc.core.configuration;

import cn.artisantc.core.persistence.entity.SchedulerJob;
import cn.artisantc.core.persistence.repository.SchedulerJobRepository;
import org.quartz.CronTrigger;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * “Quartz”在Spring框架中的配置。
 * Created by xinjie.li on 2016/12/6.
 *
 * @author xinjie.li
 * @since 1.1
 */
@Configuration(value = "QuartzConfig")
@ComponentScan(basePackages = {
        "cn.artisantc.core.service.quartz"
})
public class QuartzConfig {

    private SchedulerJobRepository schedulerJobRepository;

    private static final Logger LOG = LoggerFactory.getLogger(QuartzConfig.class);

    @Autowired
    public QuartzConfig(SchedulerJobRepository schedulerJobRepository) {
        this.schedulerJobRepository = schedulerJobRepository;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        int jobCount = 0;// 读取的任务数量
        int jobLoadedCount = 0;// 加载成功的任务数量
        List<SchedulerJob> jobLoadFailed = new ArrayList<>();// 加载失败的任务数量

        List<Trigger> triggers = new ArrayList<>();
        // 查询“调度任务信息”
        List<SchedulerJob> jobs = schedulerJobRepository.findAll();
        if (null != jobs && !jobs.isEmpty()) {
            jobCount = jobs.size();

            long startDelay = 5 * 60 * 1000;// 让任务延迟 5 分钟后开始执行
            for (SchedulerJob job : jobs) {
                // 构建“JobDetailFactoryBean”
                JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
                jobDetailFactoryBean.setName(job.getJobName());
                jobDetailFactoryBean.setGroup(job.getJobGroup());

                Class jobClass;
                try {
                    jobClass = Class.forName(job.getJobClassName());
                    jobDetailFactoryBean.setJobClass(jobClass);
                } catch (ClassNotFoundException e) {
                    LOG.error(e.getMessage(), e);
                    jobLoadFailed.add(job);
                    continue;
                }

                // 明确的让“JobDetailFactoryBean”执行一次“afterPropertiesSet”方法，用来完成“JobDetailFactoryBean”初始化后的动作
                jobDetailFactoryBean.afterPropertiesSet();

                // 构建“CronTriggerFactoryBean”
                CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
                cronTriggerFactoryBean.setJobDetail(jobDetailFactoryBean.getObject());
                cronTriggerFactoryBean.setStartDelay(startDelay);
                cronTriggerFactoryBean.setName(job.getJobName());
                cronTriggerFactoryBean.setGroup(job.getJobGroup());
                cronTriggerFactoryBean.setCronExpression(job.getCronExpression());

                /*  What to do if the job has missed its time because the previous job was running for too long.
                    Usually, it's OK to do nothing, but Quartz has other strategies as well,
                    notably 'MISFIRE_INSTRUCTION_FIRE_ONCE_NOW' */
                cronTriggerFactoryBean.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING);

                try {
                    // 明确的让“CronTriggerFactoryBean”执行一次“afterPropertiesSet”方法，用来完成“CronTriggerFactoryBean”初始化后的动作
                    cronTriggerFactoryBean.afterPropertiesSet();
                } catch (ParseException e) {
                    LOG.error(e.getMessage(), e);
                    jobLoadFailed.add(job);
                    continue;
                }

                // 放入“触发器”
                triggers.add(cronTriggerFactoryBean.getObject());

                jobLoadedCount++;
            }
        }
        LOG.info("读取的任务数量：{}", jobCount);
        LOG.info("加载成功的任务数量：{}", jobLoadedCount);
        LOG.info("加载失败的任务数量：{}", jobLoadFailed.size());
        if (!jobLoadFailed.isEmpty()) {
            for (SchedulerJob job : jobLoadFailed) {
                LOG.info("*********：{}(ID: {})，执行类：{}，执行时间规则：{}", job.getJobName(), job.getId(), job.getJobClassName(), job.getCronExpression());
            }
        }

        // 构建“SchedulerFactoryBean”
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        if (!triggers.isEmpty()) {
            Trigger[] triggerArray = new Trigger[triggers.size()];
            triggerArray = triggers.toArray(triggerArray);
            schedulerFactoryBean.setTriggers(triggerArray);
        }

        return schedulerFactoryBean;
    }
}
