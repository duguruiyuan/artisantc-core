package cn.artisantc.core.service.quartz;

import cn.artisantc.core.persistence.entity.SchedulerJob;
import cn.artisantc.core.persistence.entity.SchedulerJobExecuteRecord;
import cn.artisantc.core.persistence.repository.SchedulerJobExecuteRecordRepository;
import cn.artisantc.core.persistence.repository.SchedulerJobRepository;
import cn.artisantc.core.service.ItemService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;

import java.util.Date;

/**
 * “扫描拍品”任务。
 * Created by xinjie.li on 2016/12/8.
 *
 * @author xinjie.li
 * @since 1.1
 */
@DisallowConcurrentExecution
@Transactional
public class ScanItemsJob extends QuartzJobBean {

    private static final Logger LOG = LoggerFactory.getLogger(ScanItemsJob.class);

    private ItemService itemService;

    private SchedulerJobRepository schedulerJobRepository;

    private SchedulerJobExecuteRecordRepository schedulerJobExecuteRecordRepository;

    public ScanItemsJob() {
        this.itemService = (ItemService) ContextLoader.getCurrentWebApplicationContext().getBean("itemService");
        this.schedulerJobRepository = (SchedulerJobRepository) ContextLoader.getCurrentWebApplicationContext().getBean("schedulerJobRepository");
        this.schedulerJobExecuteRecordRepository = (SchedulerJobExecuteRecordRepository) ContextLoader.getCurrentWebApplicationContext().getBean("schedulerJobExecuteRecordRepository");
    }

    @Override
    public void executeInternal(JobExecutionContext context) throws JobExecutionException {
        final String jobName = "SCAN_ITEMS_JOB";
        LOG.info("执行“扫描拍品”任务，JobName: {}", jobName);
        itemService.scanItems();

        // 保存“调度任务的执行记录”
        SchedulerJob job = schedulerJobRepository.findByJobName(jobName);
        if (null != job) {
            SchedulerJobExecuteRecord record = new SchedulerJobExecuteRecord();
            Date date = new Date();
            record.setCreateDateTime(date);
            record.setUpdateDateTime(date);

            record.setSchedulerJobId(job.getId());
            record.setSchedulerJobName(job.getJobName());

            schedulerJobExecuteRecordRepository.save(record);
        }
    }
}
