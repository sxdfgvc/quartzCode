package com.zhengqing.modules.quartz.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhengqing.modules.common.api.BaseController;
import com.zhengqing.modules.common.dto.output.ApiResult;
import com.zhengqing.modules.quartz.dao.QuartzJobDto;
import com.zhengqing.modules.quartz.dao.QuartzJobQueryDto;
import com.zhengqing.modules.quartz.service.QuartzTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 定时任务相关操作
 */
@RestController
@RequestMapping("/api/quartz")
@Api(tags = "定时任务相关操作")
public class QuartzTaskController extends BaseController {
    @Autowired
    private QuartzTaskService quartzTaskService;

    @PostMapping(value = "/quartzList", produces = "application/json;charset=utf-8")
    @ApiOperation(value = "获取定时任务列表分页", httpMethod =     "POST", response = QuartzJobDto.class, notes = "获取定时任务列表")
    public ApiResult quartzList(@RequestBody QuartzJobQueryDto jobDto) {
        Page<QuartzJobDto> page = new Page<>(jobDto.getPage(), jobDto.getLimit());
        quartzTaskService.listPage(page, jobDto);
        return ApiResult.ok("获取定时任务列表成功", page);
    }


    @PostMapping(value = "/updateQuartzJob", produces = "application/json;charset=utf-8")
    @ApiOperation(value = "修改定时任务", httpMethod = "POST", response = String.class, notes = "修改定时任务")
    public ApiResult updateQuartzJob(@RequestBody QuartzJobDto jobDto){
        if(!CronExpression.isValidExpression(jobDto.getCronExpression())){
            return ApiResult.fail("时间表达式错误!");
        }
        Boolean isSuccess = quartzTaskService.updateQuartzJob(jobDto);
        String str = "修改定时任务成功!";
        if (!isSuccess) {
            str = "修改定时任务失败!";
        }
        return ApiResult.ok(str);
    }

    @PostMapping(value = "/deleteQuartzJob", produces = "application/json;charset=utf-8")
    @ApiOperation(value = "删除定时任务", httpMethod = "POST", response = String.class, notes = "删除定时任务")
    public ApiResult deleteQuartzJob(@RequestBody QuartzJobQueryDto jobDto) {
        Boolean isSuccess = quartzTaskService.deleteQuartzByjob(jobDto);
        String str = "删除定时任务成功!";
        if (!isSuccess) {
            str = "删除定时任务失败!";
        }
        return ApiResult.ok(str);
    }

    @PostMapping(value = "/pauseQuartzJob", produces = "application/json;charset=utf-8")
    @ApiOperation(value = "暂停定时任务", httpMethod = "POST", response = String.class, notes = "暂停定时任务")
    public ApiResult pauseQuartzJob(@RequestBody QuartzJobQueryDto jobDto){
        Boolean isSuccess = quartzTaskService.pauseQuartzJob(jobDto);
        String str = "暂停定时任务成功!";
        if (!isSuccess) {
            str = "暂停定时任务失败!";
        }
        return ApiResult.ok(str);
    }

@PostMapping(value = "/resumeQuartzJob", produces = "application/json;charset=utf-8")
    @ApiOperation(value = "恢复定时任务", httpMethod = "POST", response = String.class, notes = "恢复定时任务")
    public ApiResult resumeQuartzJob(@RequestBody QuartzJobQueryDto jobDto){
        Boolean isSuccess = quartzTaskService.resumeQuartzJob(jobDto);
        String str = "恢复定时任务成功!";
        if (!isSuccess) {
            str = "恢复定时任务失败!";
        }
        return ApiResult.ok(str);
    }

}
