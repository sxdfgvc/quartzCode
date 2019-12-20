package com.zhengqing.modules.quartz.job;

import cn.hutool.core.date.DateUtil;
import com.zhengqing.modules.quartz.annotion.QuartzJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@QuartzJob(name = "quartzTestSecond",cronExp = "0 * * * * ? * ",description = "定时任务测试two")
public class QuartzTestSecond implements Job {
    private Logger logger= LoggerFactory.getLogger(QuartzTestSecond.class);
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("定时任务执行two:"+ DateUtil.now());
    }
}
