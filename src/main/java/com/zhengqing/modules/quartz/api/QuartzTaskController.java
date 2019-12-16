package com.zhengqing.modules.quartz.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhengqing.modules.code.dto.output.DatabaseView;
import com.zhengqing.modules.common.api.BaseController;
import com.zhengqing.modules.common.dto.output.ApiResult;
import com.zhengqing.modules.quartz.dao.QuartzJobDto;
import com.zhengqing.modules.quartz.dao.QuartzJobQueryDto;
import com.zhengqing.modules.quartz.service.QuartzTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value = "获取数据库信息表列表分页", httpMethod = "POST", response = DatabaseView.class, notes = "获取数据库信息表列表分页")
    public ApiResult quartzList(@RequestBody QuartzJobQueryDto jobDto) {
        Page<QuartzJobDto> page = new Page<>(jobDto.getPage(), jobDto.getLimit());
        quartzTaskService.listPage(page,jobDto);
        return ApiResult.ok("获取数据库列表分页成功", page);
    }
}
