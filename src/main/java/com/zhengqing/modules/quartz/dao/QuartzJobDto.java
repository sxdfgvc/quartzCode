package com.zhengqing.modules.quartz.dao;

import com.zhengqing.modules.common.dto.input.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 定时任务相关Bean
 */
@Data
@ApiModel(description = "定时任务相关Bean")
public class QuartzJobDto extends BasePageQuery {
    @ApiModelProperty(value = "定时任务名称")
    private String name;
    @ApiModelProperty(value = "触发器名称")
    private String triggerName;
    @ApiModelProperty(value = "定时任务组")
    private String jobGroupName;
    @ApiModelProperty(value = "触发器组")
    private String triggerGroupName;
    @ApiModelProperty(value = "定时任务描述")
    private String description;
    @ApiModelProperty(value = "下次触发时间")
    private Long nextFireTime;
    @ApiModelProperty(value = "上次触发时间")
    private Long prevFireTime;
    @ApiModelProperty(value = "触发器状态")
    private String triggerState;
    @ApiModelProperty(value = "触发器类型")
    private String triggerType;
    @ApiModelProperty(value = "定时任务时间表达式")
    private String cronExpression;
    @ApiModelProperty(value = "定时任务开始时间")
    private Long startTime;
    @ApiModelProperty(value = "定时任务结束时间")
    private Long endTime;
}
