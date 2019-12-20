package com.zhengqing.modules.quartz.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhengqing.modules.quartz.dao.QuartzJobDto;
import com.zhengqing.modules.quartz.dao.QuartzJobQueryDto;
import com.zhengqing.modules.quartz.mapper.QuartzTaskMapper;
import com.zhengqing.modules.quartz.service.QuartzTaskService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuartzTaskServiceImpl implements QuartzTaskService {
    private static Logger logger = LoggerFactory.getLogger(QuartzTaskServiceImpl.class);
    @Autowired
    private QuartzTaskMapper quartzTaskMapper;
    @Autowired
    private Scheduler scheduler;

    @Override
    public void listPage(Page<QuartzJobDto> page, QuartzJobQueryDto filter) {
        page.setRecords(quartzTaskMapper.getQuartzList(page, filter));
    }

    @Override
    public Boolean deleteQuartzByjob(QuartzJobQueryDto jobDto) {
        Boolean isSuccess = true;
        try {
            JobKey jobKey = JobKey.jobKey(jobDto.getName(), jobDto.getJobGroupName());
            isSuccess = scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            isSuccess = false;
            logger.error("删除定时任务出错:job:{},error:{}", jobDto.getName(), e);
        }
        return isSuccess;
    }

    @Override
    public Boolean pauseQuartzJob(QuartzJobQueryDto jobDto) {
        Boolean isSuccess = true;
        try {
            JobKey jobKey = JobKey.jobKey(jobDto.getName(), jobDto.getJobGroupName());
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            isSuccess = false;
            logger.error("暂停定时任务出错:job:{},error:{}", jobDto.getName(), e);
        }
        return isSuccess;
    }

    @Override
    public Boolean updateQuartzJob(QuartzJobDto jobDto) {
        Boolean isSuccess = true;
        String cronExpression = jobDto.getCronExpression().trim();
        TriggerKey triggerKey = new TriggerKey(jobDto.getTriggerName(), jobDto.getJobGroupName());
        try {
            CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (!cronTrigger.getCronExpression().equalsIgnoreCase(cronExpression)) {
                CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                        .withDescription(jobDto.getDescription()).build();
                scheduler.rescheduleJob(triggerKey, trigger);
            }
        } catch (SchedulerException e) {
            isSuccess = false;
            logger.error("修改定时任务出错triggerName:", jobDto.getTriggerName());
        }
        return isSuccess;
    }

    @Override
    public Boolean resumeQuartzJob(QuartzJobQueryDto jobDto) {
        Boolean isSuccess = true;
        try {
            JobKey jobKey = JobKey.jobKey(jobDto.getName(), jobDto.getJobGroupName());
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            isSuccess = false;
            logger.error("恢复定时任务出错:job:{},error:{}", jobDto.getName(), e);
        }
        return isSuccess;
    }
}
