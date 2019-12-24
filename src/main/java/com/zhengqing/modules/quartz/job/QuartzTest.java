package com.zhengqing.modules.quartz.job;

import cn.hutool.core.date.DateUtil;
import com.zhengqing.modules.quartz.annotion.QuartzJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@QuartzJob(name = "quartzTestFirst",cronExp = "0 0/1 * * * ? ",description = "定时任务测试one")
public class QuartzTest implements Job {
    private Logger logger= LoggerFactory.getLogger(QuartzTest.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        String sql="INSERT INTO `t_quartz_data`(job_name,description,wrong_quantity,create_date,update_date) VALUES(?,?,?,?,?)";
//        jdbcTemplate.update(sql,"quartzTestFirst","定时任务测试one",DateUtil.thisMinute(),DateUtil.now(),DateUtil.now());
        logger.info("定时任务执行one:"+ DateUtil.now());
    }
}
