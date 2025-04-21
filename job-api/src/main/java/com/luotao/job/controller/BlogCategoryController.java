package com.luotao.job.controller;

import com.luotao.job.service.BlogCategoryService;
import com.luotao.job.utils.ResponseResult;
import com.luotao.job.vo.BlogCategoryVo;
import com.luotao.job.vo.BlogCategoryCreateVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author luotao
 * @description 博客分类控制器
 */
@Slf4j
@RestController
@RequestMapping("/blog/categories")
@Api(tags = "博客分类管理接口")
public class BlogCategoryController {

    @Resource
    private BlogCategoryService blogCategoryService;

    /**
     * 获取所有分类列表
     */
    @GetMapping
    @ApiOperation("获取所有分类列表")
    public ResponseResult<List<BlogCategoryVo>> getAllCategories() {
        return blogCategoryService.getAllCategories();
    }

    /**
     * 创建分类
     */
    @PostMapping
    @ApiOperation("创建分类")
    public ResponseResult<Long> createCategory(@RequestBody BlogCategoryCreateVo categoryVo) {
        try {
            log.info("创建分类，参数：{}", categoryVo);
            
            if (!StringUtils.hasText(categoryVo.getName())) {
                return new ResponseResult<>(400, "分类名称不能为空");
            }
            
            return blogCategoryService.createCategory(
                categoryVo.getName(), 
                categoryVo.getDescription(), 
                categoryVo.getSort()
            );
        } catch (Exception e) {
            log.error("创建分类失败: ", e);
            return new ResponseResult<>(500, "创建分类失败");
        }
    }

    /**
     * 更新分类
     */
    @PutMapping("/{categoryId}")
    @ApiOperation("更新分类")
    public ResponseResult<Void> updateCategory(
            @ApiParam("分类ID") @PathVariable Long categoryId,
            @ApiParam("分类名称") @RequestParam(required = false) String name,
            @ApiParam("分类描述") @RequestParam(required = false) String description,
            @ApiParam("排序") @RequestParam(required = false) Integer sort) {
        System.out.println(categoryId );
        System.out.println(name );
        System.out.println(description );
        System.out.println(sort );
        return blogCategoryService.updateCategory(categoryId, name, description, sort);
    }

    /**
     * 删除分类
     */
    @DeleteMapping("/{categoryId}")
    @ApiOperation("删除分类")
    public ResponseResult<Void> deleteCategory(
            @ApiParam("分类ID") @PathVariable Long categoryId) {
        return blogCategoryService.deleteCategory(categoryId);
    }
} 