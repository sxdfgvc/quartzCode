package com.zhengqing.modules.quartz.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhengqing.modules.quartz.dao.QuartzJobDto;
import com.zhengqing.modules.quartz.dao.QuartzJobQueryDto;

import java.util.List;

/**
 * 定时任务相关
 */
public interface QuartzTaskService {
    void listPage(Page<QuartzJobDto> page, QuartzJobQueryDto filter);
}
