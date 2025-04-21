package com.luotao.job.service.impl;

import com.luotao.job.domain.JobCategory;
import com.luotao.job.mapper.JobCategoryMapper;
import com.luotao.job.service.JobCategoryService;
import com.luotao.job.utils.ResponseResult;
import com.luotao.job.utils.enums.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.swing.*;
import java.util.HashMap;

/**
 * @Classname JobCategoryServiceImpl
 * @Description TODO
 * @Version 1.css.0.0
 * @Date 2024/12/24 16:56
 * @Author LuoTao
 */
@Service
public class JobCategoryServiceImpl implements JobCategoryService {
    /*让 Spring 容器自动为 jobCategoryMapper 成员变量注入一个实现了 JobCategoryMapper 接口的实例对象。
    javax.swing.Spring 会根据配置（例如 MyBatis 的配置）动态生成JobCategoryMapper 实现类的代理对象。这个代理对象会被注入到 jobCategoryMapper 中
     */
    @Resource// 注入
    private JobCategoryMapper jobCategoryMapper;
    /**
     * 获取所有岗位类别
     **/
    @Override
    public ResponseResult getAllJobCategory() {
        return new ResponseResult(jobCategoryMapper.allJobCategory());
    }

    /**
     * 新增岗位类别
     *
     * @param jobCategory
     */
    @Override
    public ResponseResult addJobCategory(JobCategory jobCategory) {
        HashMap<String, Integer> idMap = new HashMap<>();
        int increamentID = jobCategoryMapper.insertJobCategory(jobCategory);
//        System.out.println(increamentID);
        if (increamentID >0 ){
            idMap.put("id", jobCategory.getId());
            return new ResponseResult(idMap);
        }
        return new ResponseResult(ResultCodeEnum.SERVER_ERROR);
    }

    /**
     * 删除岗位类别
     *
     * @param id
     */
    @Override
    public ResponseResult deleteJobCategory(Integer id) {
        if (jobCategoryMapper.deleteByPrimaryKey(id) > 0) {
            return new ResponseResult();
        }
        return new ResponseResult(ResultCodeEnum.DB_ERROR);
    }

    /**
     * 更新岗位类别
     *
     * @param jobCategory
     */
    @Override
    public ResponseResult updateJobCategory(JobCategory jobCategory) {
        if (jobCategoryMapper.updateByPrimaryKey(jobCategory)>0){
            return new ResponseResult();
        }
        return new ResponseResult(ResultCodeEnum.DB_ERROR);
    }

}
