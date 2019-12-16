package com.zhengqing.modules.quartz.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhengqing.modules.quartz.dao.QuartzJobDto;
import com.zhengqing.modules.quartz.dao.QuartzJobQueryDto;
import com.zhengqing.modules.quartz.mapper.QuartzTaskMapper;
import com.zhengqing.modules.quartz.service.QuartzTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuartzTaskServiceImpl implements QuartzTaskService {
    @Autowired
    private QuartzTaskMapper quartzTaskMapper;
    @Override
    public void listPage(Page<QuartzJobDto> page, QuartzJobQueryDto filter) {
        page.setRecords(quartzTaskMapper.getQuartzList(page,filter));
    }
}
