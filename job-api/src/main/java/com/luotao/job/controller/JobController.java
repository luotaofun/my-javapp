package com.luotao.job.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.luotao.job.domain.Jobs;
import com.luotao.job.service.JobsService;
import com.luotao.job.utils.ResponseResult;
import com.luotao.job.vo.JobsByCategoryVo;
import com.luotao.job.vo.JobsByCityVo;
import com.luotao.job.vo.JobsByProvinceVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Classname JobController
 * @Description TODO
 * @Version 1.0.0
 * @Date 2025/3/25 17:47
 * @Author LuoTao
 */
@RestController
@RequestMapping("/job")
@Slf4j
@Api(tags = "岗位管理相关接口")
public class JobController {
    @Resource
    private JobsService jobsService;

    @ApiOperation("根据id获取岗位数据")
    @GetMapping("/v1/{id}")
    public ResponseResult getById(@PathVariable Integer id){
        Jobs byId = jobsService.getById(id);
        if (byId != null){
            return new ResponseResult(byId);
        }else{
            return new ResponseResult(401, "不存在对应id的数据");
        }
    }

    @GetMapping("/v1/page/{page}")
    @ApiOperation("分页获取岗位数据")
    public ResponseResult getByPage(@PathVariable Integer page){
        Integer pageSize=20;
        IPage<Jobs> jobsByPage = jobsService.getJobsByPage(page, pageSize);
        log.info("总行数:{}",jobsByPage.getTotal());
        log.info("总页数:{}",jobsByPage.getPages());
        log.info("当前页:{}",jobsByPage.getCurrent());
        return new ResponseResult(jobsByPage);
    }

    @GetMapping("/v1/category/statistics")
    @ApiOperation("按类别统计岗位")
    public ResponseResult getByCategory(){
        List<JobsByCategoryVo> jobsByCategoryVos = jobsService.countByCategoryName();
        return new ResponseResult(jobsByCategoryVos);
    }
    // 按城市统计岗位
    @GetMapping("/v1/city/statistics")
    @ApiOperation("按城市统计岗位")
    public ResponseResult getByCity(){
        List<JobsByCityVo> JobsByCityVos = jobsService.countByCity();
        return new ResponseResult(JobsByCityVos);
    }

    // 按省份统计岗位
    @GetMapping("/v1/province/statistics")
    @ApiOperation("按省份统计岗位")
    public ResponseResult getByProvince(){
        List<JobsByProvinceVo> jobsByProvinceVos = jobsService.countByProvince();
        return new ResponseResult(jobsByProvinceVos);
    }
}
