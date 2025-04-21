package com.luotao.job.mapper;

import com.luotao.job.domain.Jobs;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luotao.job.vo.JobsByCategoryVo;
import com.luotao.job.vo.JobsByCityVo;
import com.luotao.job.vo.JobsByProvinceVo;

import java.util.List;

/**
* @author T
* @description 针对表【jobs(岗位表)】的数据库操作Mapper
* @createDate 2025-03-25 17:30:32
* @Entity com.luotao.job.domain.Jobs
*/
public interface JobsMapper extends BaseMapper<Jobs> {
    // 按类别统计岗位数量
    List<JobsByCategoryVo> countByCategoryName();

    // 按城市统计岗位数量
    List<JobsByCityVo> countByCity();

    // 按省份统计岗位数量
    List<JobsByProvinceVo> countByProvince();
}




