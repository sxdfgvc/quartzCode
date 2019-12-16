package com.zhengqing.modules.quartz.job;

import com.zhengqing.modules.quartz.annotion.QuartzJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@QuartzJob(name = "quartzTest",cronExp = "0 * * * * ? * ",description = "定时任务测试")
public class QuartzTest implements Job {
    private Logger logger= LoggerFactory.getLogger(QuartzTest.class);
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("定时任务执行");
    }
}
