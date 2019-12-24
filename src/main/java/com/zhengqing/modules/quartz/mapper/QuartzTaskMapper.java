package com.zhengqing.modules.quartz.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.zhengqing.modules.quartz.dao.QuartzJobDto;
import com.zhengqing.modules.quartz.dao.QuartzJobQueryDto;
import com.zhengqing.modules.quartz.dao.QuartzWrongDateDto;
import com.zhengqing.modules.quartz.dao.QuartzWrongQueryDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface QuartzTaskMapper {
    List<QuartzJobDto> getQuartzList(Pagination page, @Param("filter") QuartzJobQueryDto filter);

    List<QuartzWrongDateDto> getWrongJobDate(@Param("filter") QuartzWrongQueryDto jobDto);

    List<Map<String, Object>> getWrongJobDateList(QuartzWrongQueryDto jobDto);
}
