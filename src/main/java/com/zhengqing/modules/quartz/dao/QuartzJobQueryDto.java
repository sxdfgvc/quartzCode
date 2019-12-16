package com.zhengqing.modules.quartz.dao;

import com.zhengqing.modules.common.dto.input.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 定时任务相关Bean
 */
@Data
@ApiModel(description = "定时任务查询相关Bean")
public class QuartzJobQueryDto extends BasePageQuery {
    @ApiModelProperty(value = "定时任务名称")
    private String name;
    @ApiModelProperty(value = "触发器名称")
    private String triggerName;
    @ApiModelProperty(value = "定时任务组")
    private String jobGroupName;
    @ApiModelProperty(value = "触发器组")
    private String triggerGroupName;
    @ApiModelProperty(value = "定时任务状态")
    private boolean volatileJob;
    @ApiModelProperty(value = "定时任务描述")
    private String description;
}
