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
public class QuartzWrongDateDto extends BasePageQuery {
    @ApiModelProperty(value = "主键ID")
    private Integer id;
    @ApiModelProperty(value = "定时任务名称")
    private String jobName;
    @ApiModelProperty(value = "定时任务描述")
    private String description;
    @ApiModelProperty(value = "定时任务错误数量")
    private String wrongQuantity;
    @ApiModelProperty(value = "创建时间")
    private Date createdDate;
    @ApiModelProperty(value = "修改时间")
    private Date updateDate;
}
