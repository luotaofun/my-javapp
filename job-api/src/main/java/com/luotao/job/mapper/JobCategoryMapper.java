package com.luotao.job.mapper;

import com.luotao.job.domain.JobCategory;
import java.util.List;

/**
 * @Classname JobCategoryMapper
 */
//@Mapper
public interface JobCategoryMapper {
    /**
     查询数据
    **/
    List<JobCategory> allJobCategory();

    /**
        添加数据
    **/
    int insertJobCategory(JobCategory jobCategory);

    /**
     * 删除数据
     **/
    int deleteByPrimaryKey(Integer id);

    /**
     修改数据
     **/
    int updateByPrimaryKey(JobCategory jobCategory);

}
