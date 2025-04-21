package com.luotao.job.service;
import com.luotao.job.domain.JobCategory;
import com.luotao.job.utils.ResponseResult;

public interface JobCategoryService {
    /**
        获取所有岗位类别
    **/
    ResponseResult getAllJobCategory();

    /**
     * 新增岗位类别
     **/
    ResponseResult addJobCategory(JobCategory jobCategory);

    /**
     * 删除岗位类别
     **/
    ResponseResult deleteJobCategory(Integer id);
    /**
     * 更新岗位类别
     **/
    ResponseResult updateJobCategory(JobCategory jobCategory);

}
