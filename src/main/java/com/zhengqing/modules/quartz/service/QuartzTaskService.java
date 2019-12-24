package com.zhengqing.modules.quartz.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhengqing.modules.quartz.dao.QuartzJobDto;
import com.zhengqing.modules.quartz.dao.QuartzJobQueryDto;
import com.zhengqing.modules.quartz.dao.QuartzWrongDateDto;
import com.zhengqing.modules.quartz.dao.QuartzWrongQueryDto;

import java.util.List;
import java.util.Map;

/**
 * 定时任务相关
 */
public interface QuartzTaskService {
    void listPage(Page<QuartzJobDto> page, QuartzJobQueryDto filter);

    Boolean deleteQuartzByjob(QuartzJobQueryDto jobDto);

    Boolean pauseQuartzJob(QuartzJobQueryDto jobDto);

    Boolean updateQuartzJob(QuartzJobDto jobDto);

    Boolean resumeQuartzJob(QuartzJobQueryDto jobDto);

    List<QuartzWrongDateDto> getWrongJobDate(QuartzWrongQueryDto jobDto);

    Map<String,Integer> getWrongJobDateList(QuartzWrongQueryDto jobDto);
}
