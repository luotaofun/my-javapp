package com.luotao.job.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luotao.job.domain.Jobs;
import com.luotao.job.service.JobsService;
import com.luotao.job.mapper.JobsMapper;
import com.luotao.job.vo.JobsByCategoryVo;
import com.luotao.job.vo.JobsByCityVo;
import com.luotao.job.vo.JobsByProvinceVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author T
* @description 针对表【jobs(岗位表)】的数据库操作Service实现
* @createDate 2025-03-25 17:30:32
*/
@Service
public class JobsServiceImpl extends ServiceImpl<JobsMapper, Jobs>
    implements JobsService{
    //Spring 会根据配置（例如 MyBatis 的配置）动态生成JobsMapper 实现类的代理对象。这个代理对象会被注入到 jobsMapper 中
    @Resource
    private JobsMapper jobsMapper;

    /**
     * @Description 定义了getJobsByPage方法，用于根据页码和每页大小查询岗位信息。
     * 使用QueryWrapper构造查询条件，选择特定字段并按publish_time降序排序。
     * 调用jobsMapper.selectPage执行分页查询并返回结果。
     * @return 分页对象结果集
     * @param pageNum 当前页码
     * @param pageSize 每页的数据条数
     **/
    @Override
    public IPage<Jobs> getJobsByPage(int pageNum, int pageSize) {
        Page<Jobs> page = new Page<>(pageNum, pageSize);// 创建分页对象
        QueryWrapper<Jobs> jobsQueryWrapper = new QueryWrapper<>();// 创建查询条件包装器构造查询条件
        jobsQueryWrapper.select("id",
                "job_title",
                "category_name",
                "salary",
                "city",
                "company",
                "company_info",
                "publish_time"
        );
        jobsQueryWrapper.orderByDesc("publish_time");
        Page<Jobs> jobsPage = jobsMapper.selectPage(page, jobsQueryWrapper); // 执行分页查询
        return jobsPage;
    }

    @Override
    public List<JobsByCategoryVo> countByCategoryName() {
        List<JobsByCategoryVo> jobsByCategoryVos = jobsMapper.countByCategoryName();
        return jobsByCategoryVos;
    }

    @Override
    public List<JobsByCityVo> countByCity() {
        List<JobsByCityVo> jobsByCityVos = jobsMapper.countByCity();
        return jobsByCityVos;
    }

    @Override
    public List<JobsByProvinceVo> countByProvince() {
        List<JobsByProvinceVo> jobsByProvinceVos = jobsMapper.countByProvince();
        return jobsByProvinceVos;
    }
}




