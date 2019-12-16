package com.zhengqing.modules.quartz.annotion;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;


/**
 * 定时任务相关
 * @author Fan
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@Scope("prototype")
public @interface QuartzJob {

    /**
     * 任务名称
     * @return
     */
    String name();

    /**
     * 组
     * @return
     */
    String group() default "DEFAULT_GROUP";

    /**
     * 触发时间
     */
    String cronExp();

    /**
     * 定时任务描述
     */
    String description() default "";

    /**
     * 是否触发
     */
    boolean unschedule() default false;

    /**
     * 是否删除
     * @return
     */
    boolean deleteJob() default false;


}

