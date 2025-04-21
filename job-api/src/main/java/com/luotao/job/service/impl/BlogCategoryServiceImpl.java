package com.luotao.job.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luotao.job.domain.BlogCategory;
import com.luotao.job.domain.BlogPost;
import com.luotao.job.mapper.BlogCategoryMapper;
import com.luotao.job.mapper.BlogPostMapper;
import com.luotao.job.service.BlogCategoryService;
import com.luotao.job.utils.ResponseResult;
import com.luotao.job.vo.BlogCategoryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author luotao
 * @description 博客分类服务实现类
 */
@Slf4j
@Service
public class BlogCategoryServiceImpl extends ServiceImpl<BlogCategoryMapper, BlogCategory> implements BlogCategoryService {

    @Resource
    private BlogPostMapper blogPostMapper;

    /**
     * 获取所有分类列表
     *
     * @return 分类列表
     */
    @Override
    public ResponseResult<List<BlogCategoryVo>> getAllCategories() {
        // 1. 查询所有分类及其文章数量
        List<BlogCategory> categories = baseMapper.selectCategoryWithCount();
        
        // 2. 转换为VO对象
        List<BlogCategoryVo> categoryVos = categories.stream().map(category -> {
            BlogCategoryVo categoryVo = new BlogCategoryVo();
            categoryVo.setId(category.getId());
            categoryVo.setName(category.getName());
            categoryVo.setDescription(category.getDescription());
            
            // 查询该分类下的文章数量
            LambdaQueryWrapper<BlogPost> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(BlogPost::getCategoryId, category.getId())
                    .eq(BlogPost::getStatus, 1); // 只统计已发布的文章
//            int count = blogPostMapper.selectCount(queryWrapper);
            Long countLong = blogPostMapper.selectCount(queryWrapper);
            int count = (countLong == null) ? 0 : countLong.intValue(); // 显式转换并处理 null 情
            categoryVo.setPostCount(count);
            
            return categoryVo;
        }).collect(Collectors.toList());
        
        return new ResponseResult<>(200, "获取成功", categoryVos);
    }

    /**
     * 创建分类
     *
     * @param name        分类名称
     * @param description 分类描述
     * @param sort        排序
     * @return 创建结果
     */
    @Override
    public ResponseResult<Long> createCategory(String name, String description, Integer sort) {
        try {
            // 1. 检查分类名称是否已存在
            LambdaQueryWrapper<BlogCategory> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(BlogCategory::getName, name);
            if (this.count(queryWrapper) > 0) {
                return new ResponseResult<>(400, "分类名称已存在");
            }

            // 2. 创建分类
            BlogCategory category = new BlogCategory();
            category.setName(name);
            category.setDescription(description);
            category.setSort(sort != null ? sort : 0);
            category.setCreateTime(LocalDateTime.now());
            category.setUpdateTime(LocalDateTime.now());

            // 3. 保存分类
            this.save(category);

            return new ResponseResult<>(200, "创建成功", category.getId());
        } catch (Exception e) {
            log.error("创建分类失败: ", e);
            return new ResponseResult<>(500, "创建分类失败");
        }
    }

    /**
     * 更新分类
     *
     * @param categoryId  分类ID
     * @param name        分类名称
     * @param description 分类描述
     * @param sort        排序
     * @return 更新结果
     */
    @Override
    public ResponseResult<Void> updateCategory(Long categoryId, String name, String description, Integer sort) {
        // 1. 检查分类是否存在
        BlogCategory category = getById(categoryId);
        if (category == null) {
            return new ResponseResult<>(404, "分类不存在");
        }
        
        // 2. 如果修改了名称，检查名称是否已存在
        if (StringUtils.hasText(name) && !name.equals(category.getName())) {
            LambdaQueryWrapper<BlogCategory> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(BlogCategory::getName, name);
            if (baseMapper.selectCount(queryWrapper) > 0) {
                return new ResponseResult<>(400, "分类名称已存在");
            }
            category.setName(name);
        }
        
        // 3. 更新其他字段
        if (StringUtils.hasText(description)) {
            category.setDescription(description);
        }
        
        if (sort != null) {
            category.setSort(sort);
        }
        
        category.setUpdateTime(LocalDateTime.now());
        updateById(category);
        
        return new ResponseResult<>(200, "更新成功");
    }

    /**
     * 删除分类
     *
     * @param categoryId 分类ID
     * @return 删除结果
     */
    @Override
    public ResponseResult<Void> deleteCategory(Long categoryId) {
        // 1. 检查分类是否存在
        BlogCategory category = getById(categoryId);
        if (category == null) {
            return new ResponseResult<>(404, "分类不存在");
        }
        
        // 2. 检查是否有文章使用该分类
        LambdaQueryWrapper<BlogPost> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BlogPost::getCategoryId, categoryId);
        long count = blogPostMapper.selectCount(queryWrapper);
        
        if (count > 0) {
            return new ResponseResult<>(400, "该分类下有" + count + "篇文章，无法删除");
        }
        
        // 3. 删除分类
        removeById(categoryId);
        
        return new ResponseResult<>(200, "删除成功");
    }
}