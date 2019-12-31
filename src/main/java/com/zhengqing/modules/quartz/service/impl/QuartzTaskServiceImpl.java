package com.zhengqing.modules.quartz.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhengqing.modules.quartz.dao.QuartzJobDto;
import com.zhengqing.modules.quartz.dao.QuartzJobQueryDto;
import com.zhengqing.modules.quartz.dao.QuartzWrongDateDto;
import com.zhengqing.modules.quartz.dao.QuartzWrongQueryDto;
import com.zhengqing.modules.quartz.mapper.QuartzTaskMapper;
import com.zhengqing.modules.quartz.service.QuartzTaskService;
import org.apache.commons.lang.StringUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public List<QuartzWrongDateDto> getWrongJobDate(QuartzWrongQueryDto jobDto) {
        List<QuartzWrongDateDto> list=quartzTaskMapper.getWrongJobDate(jobDto);
        return list;
    }

    @Override
    public Map<String, Integer> getWrongJobDateList(QuartzWrongQueryDto jobDto) {
        Map<String, Integer> map = new HashMap<>();
        List<Map<String, Object>> mapList = quartzTaskMapper.getWrongJobDateList(jobDto);
        if (mapList != null && mapList.size() > 0) {
            for (Map<String, Object> mm : mapList) {
                String nowTime = ObjectUtil.toString(mm.get("nowTime"));
                int quarity = (int) mm.get("nowTime");
                if (map.get(nowTime) != null) {
                    map.put(nowTime, map.get(nowTime) + quarity);
                } else {
                    map.put(nowTime, quarity);
                }
            }
        }
        return map;
    }

    @Override
    public String triggerQuartzJobNow(List<QuartzJobDto> jobDto) {
        List<String> errorList=new ArrayList<>();
        for (QuartzJobDto quartzJobDto : jobDto) {
            try {
                scheduler.triggerJob(new JobKey(quartzJobDto.getName(),quartzJobDto.getJobGroupName()));
            } catch (SchedulerException e) {
                logger.error("触发{}定时任务失败",quartzJobDto.getName());
                errorList.add(quartzJobDto.getName());
            }
        }
        return StringUtils.join(errorList,",");
    }
}
