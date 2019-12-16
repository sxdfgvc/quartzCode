package com.zhengqing.modules.quartz.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.zhengqing.modules.quartz.dao.QuartzJobDto;
import com.zhengqing.modules.quartz.dao.QuartzJobQueryDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuartzTaskMapper {
    List<QuartzJobDto> getQuartzList(Pagination page, @Param("filter") QuartzJobQueryDto filter);
}
