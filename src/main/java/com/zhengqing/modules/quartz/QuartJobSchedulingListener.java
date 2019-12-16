package com.zhengqing.modules.quartz;

import com.google.common.collect.Maps;
import com.zhengqing.modules.quartz.annotion.QuartzJob;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class QuartJobSchedulingListener implements ApplicationListener<ContextRefreshedEvent> {
    private Logger logger = LoggerFactory.getLogger(QuartJobSchedulingListener.class);
    private static final String JOBS_TO_DELETE = "jobs_to_delete";
    private static final String JOBS_TO_UNSCHEDULE = "jobs_to_unschedule";
    private static final String JOBS_TO_SCHEDULE = "jobs_to_schedule";
    @Autowired
    private Scheduler scheduler;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            ApplicationContext applicationContext = event.getApplicationContext();
            Map<String, List<Map<Trigger, JobDetail>>> cronTriggerBeans = this.loadCronTriggerBeans(applicationContext);
            this.scheduleJobs(cronTriggerBeans);
        } catch (Exception e) {
            logger.error("Job schedule error: ", e);
        }
    }

    private void scheduleJobs(Map<String, List<Map<Trigger, JobDetail>>> cronTriggerBeans) {
        List<Map<Trigger, JobDetail>> jobsToSchedule = cronTriggerBeans.get(JOBS_TO_SCHEDULE);
        for (Map<Trigger, JobDetail> triggerJobDetailMap : jobsToSchedule) {
            for (Map.Entry<Trigger, JobDetail> triggerJobDetailEntry : triggerJobDetailMap.entrySet()) {
                Trigger trigger = triggerJobDetailEntry.getKey();
                boolean needReschedule = false;
                try {
                    scheduler.scheduleJob(triggerJobDetailEntry.getValue(), trigger);
                } catch (SchedulerException e) {
                    logger.warn("Job instance " + trigger.getKey().getName() + " schedule error, try to reschedule it.");
                    needReschedule = true;
                }
                if (needReschedule) {
                    try {
                        Date rescheduleDate = scheduler.rescheduleJob(trigger.getKey(), trigger);
                        if (rescheduleDate != null) {
                            logger.info("trigger.getName():" + trigger.getKey().getName() + ",rescheduleDate:" + rescheduleDate);
                        } else {
                            logger.info("trigger.getName():" + trigger.getKey().getName() + ",rescheduleDate:null");
                        }
                    } catch (SchedulerException e) {
                        logger.error("Job reschedule error, with exception: ", e);
                    }
                }
            }
        }

        List<Map<Trigger, JobDetail>> jobsToUnschedule = cronTriggerBeans.get(JOBS_TO_UNSCHEDULE);
        for (Map<Trigger, JobDetail> triggerJobDetailMap : jobsToUnschedule) {
            for (Map.Entry<Trigger, JobDetail> triggerJobDetailEntry : triggerJobDetailMap.entrySet()) {
                Trigger trigger = triggerJobDetailEntry.getKey();
                try {
                    boolean flag = scheduler.unscheduleJob(trigger.getKey());
                    logger.info("trigger.getName():" + trigger.getKey().getName() + ",flag:" + flag);
                } catch (SchedulerException e) {
                    logger.error("Failed to unschedule the trigger: " + trigger.getKey().getName());
                    logger.error("With below exception: ", e);
                }
            }
        }

        List<Map<Trigger, JobDetail>> jobsToDelete = cronTriggerBeans.get(JOBS_TO_DELETE);
        for (Map<Trigger, JobDetail> triggerJobDetailMap : jobsToDelete) {
            for (Map.Entry<Trigger, JobDetail> triggerJobDetailEntry : triggerJobDetailMap.entrySet()) {
                Trigger trigger = triggerJobDetailEntry.getKey();
                try {
                    boolean flag = scheduler.deleteJob(triggerJobDetailEntry.getValue().getKey());
                    logger.info("delete trigger.getName():" + trigger.getKey().getName() + ",flag:" + flag);
                } catch (SchedulerException e) {
                    logger.error("Failed to delete the job: " + trigger.getKey().getName() + ", with below error: ", e);
                }
            }
        }

    }

    private Map<String, List<Map<Trigger, JobDetail>>> loadCronTriggerBeans(ApplicationContext applicationContext) {
        Map<String, Object> quartzJobBeans = applicationContext.getBeansWithAnnotation(QuartzJob.class);
        Set<String> beanNames = quartzJobBeans.keySet();
        Map<String, List<Map<Trigger, JobDetail>>> cronTriggerBeans = Maps.newHashMap();
        cronTriggerBeans.put(JOBS_TO_DELETE, new ArrayList<>());
        cronTriggerBeans.put(JOBS_TO_UNSCHEDULE, new ArrayList<>());
        cronTriggerBeans.put(JOBS_TO_SCHEDULE, new ArrayList<>());
        for (String beanName : beanNames) {
            Map<Trigger, JobDetail> map = new HashMap<>();
            Trigger trigger = null;
            JobDetail jobDetail = null;
            Object object = quartzJobBeans.get(beanName);
            try {
                map = this.buildCronTriggerBean(object);
            } catch (Exception e) {
                logger.error("构造Trigger或detail出错!e:{}", e);
            }
            if (map != null && map.size() > 0) {
                QuartzJob quartzJobAnnotation = AnnotationUtils.findAnnotation(object.getClass(), QuartzJob.class);
                if (quartzJobAnnotation.deleteJob()) {
                    cronTriggerBeans.get(JOBS_TO_DELETE).add(map);
                } else if (quartzJobAnnotation.unschedule()) {
                    cronTriggerBeans.get(JOBS_TO_UNSCHEDULE).add(map);
                } else {
                    cronTriggerBeans.get(JOBS_TO_SCHEDULE).add(map);
                }
            }
        }
        return cronTriggerBeans;
    }


    private Map<Trigger, JobDetail> buildCronTriggerBean(Object job) {
        Map<Trigger, JobDetail> map = new HashMap<>();
        Trigger trigger;
        JobDetail jobDetail;
        QuartzJob quartzJobAnnotation = AnnotationUtils.findAnnotation(job.getClass(), QuartzJob.class);
        if (Job.class.isAssignableFrom(job.getClass())) {
            trigger = TriggerBuilder.newTrigger().withIdentity(quartzJobAnnotation.name() + "Trigger", quartzJobAnnotation.group())
                    .withDescription(quartzJobAnnotation.description()).withSchedule(CronScheduleBuilder.cronSchedule(quartzJobAnnotation.cronExp())).build();
            jobDetail = JobBuilder.newJob((Class<? extends Job>) job.getClass()).withIdentity(quartzJobAnnotation.name(), quartzJobAnnotation.group()).withDescription(quartzJobAnnotation.description()).build();
            map.put(trigger, jobDetail);
            return map;
        } else {
            throw new RuntimeException(job.getClass() + " doesn't implemented " + Job.class);
        }
    }


}
