package com.luotao.job.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.luotao.job.domain.Jobs;
import com.baomidou.mybatisplus.extension.service.IService;
import com.luotao.job.vo.JobsByCategoryVo;
import com.luotao.job.vo.JobsByCityVo;
import com.luotao.job.vo.JobsByProvinceVo;

import java.util.List;

/**
* @author T
* @description 针对表【jobs(岗位表)】的数据库操作Service
* @createDate 2025-03-25 17:30:32
*/
public interface JobsService extends IService<Jobs> {
    /**
     * @Description 分页查询
     * @Author LuoTao
     * @Date 2025/3/26 17:49
     * @return 分页对象结果集
     * @param pageNum 当前页码
     * @param pageSize 每页的数据条数
     **/
    IPage<Jobs> getJobsByPage(int pageNum, int pageSize);

    // 按类别统计岗位数量
    List<JobsByCategoryVo> countByCategoryName();

    // 按城市统计岗位数量
    List<JobsByCityVo> countByCity();

    // 按省份统计岗位数量
    List<JobsByProvinceVo> countByProvince();
}
