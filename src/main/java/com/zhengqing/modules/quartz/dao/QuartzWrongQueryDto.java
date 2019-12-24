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
@ApiModel(description = "定时任务错误数据相关Bean")
public class QuartzWrongQueryDto extends BasePageQuery {
    @ApiModelProperty(value = "主键ID")
    private Integer id;
    @ApiModelProperty(value = "定时任务名称")
    private String jobName;
    @ApiModelProperty(value = "定时任务描述")
    private String description;
    @ApiModelProperty(value = "开始时间")
    private String startTime;
    @ApiModelProperty(value = "结束时间")
    private String endTime;
}
