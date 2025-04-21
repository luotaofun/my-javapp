package com.luotao.job.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.luotao.job.domain.BlogCategory;
import com.luotao.job.utils.ResponseResult;
import com.luotao.job.vo.BlogCategoryVo;

import java.util.List;

/**
 * @author luotao
 * @description 博客分类服务接口
 */
public interface BlogCategoryService extends IService<BlogCategory> {
    /**
     * 获取所有分类列表
     *
     * @return 分类列表
     */
    ResponseResult<List<BlogCategoryVo>> getAllCategories();
    
    /**
     * 创建分类
     *
     * @param name 分类名称
     * @param description 分类描述
     * @param sort 排序
     * @return 创建结果
     */
    ResponseResult<Long> createCategory(String name, String description, Integer sort);
    
    /**
     * 更新分类
     *
     * @param categoryId 分类ID
     * @param name 分类名称
     * @param description 分类描述
     * @param sort 排序
     * @return 更新结果
     */
    ResponseResult<Void> updateCategory(Long categoryId, String name, String description, Integer sort);
    
    /**
     * 删除分类
     *
     * @param categoryId 分类ID
     * @return 删除结果
     */
    ResponseResult<Void> deleteCategory(Long categoryId);
}
