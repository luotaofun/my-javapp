package com.luotao.job.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luotao.job.domain.BlogCarousel;
import com.luotao.job.service.BlogCarouselService;
import com.luotao.job.utils.ResponseResult; // 确保导入了 ResponseResult
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @Classname blogCarouselController
 * @Description 博客轮播图控制器
 * @Version 1.0.0
 * @Date 2025/4/4 23:46
 * @Author LuoTao
 */
@RestController
@RequestMapping("/blog/carousel") // 定义基础路径
public class blogCarouselController {

    @Autowired
    private BlogCarouselService blogCarouselService;

    /**
     * 获取所有启用的、在有效期内的轮播图 (给前台展示用)
     * 按 sort 升序排序
     * @return ResponseResult 包含轮播图列表
     */
    @GetMapping
    public ResponseResult<List<BlogCarousel>> getActiveCarousels() {
        Date now = new Date();
        LambdaQueryWrapper<BlogCarousel> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BlogCarousel::getStatus, 1) // 状态为启用
                .le(BlogCarousel::getStartTime, now) // 开始时间 <= 当前时间
                .ge(BlogCarousel::getEndTime, now)   // 结束时间 >= 当前时间
                .orderByAsc(BlogCarousel::getSort); // 按sort字段升序排序
        List<BlogCarousel> list = blogCarouselService.list(queryWrapper);
        return new ResponseResult<>(list); // 使用 ResponseResult 包装数据
    }

    /**
     * 分页获取所有轮播图 (给后台管理用)
     * @param page 当前页码, 默认为1
     * @param pageSize 每页数量, 默认为10
     * @return ResponseResult 包含分页数据
     */
    @GetMapping("/admin")
    public ResponseResult<Page<BlogCarousel>> getAllCarouselsForAdmin(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<BlogCarousel> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<BlogCarousel> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(BlogCarousel::getSort); // 按sort字段升序排序
        Page<BlogCarousel> carouselPage = blogCarouselService.page(pageInfo, queryWrapper);
        return new ResponseResult<>(carouselPage);
    }

    /**
     * 获取单个轮播图详情
     * @param id 轮播图ID
     * @return ResponseResult 包含轮播图信息
     */
    @GetMapping("/{id}")
    public ResponseResult<BlogCarousel> getCarouselById(@PathVariable Long id) {
        BlogCarousel carousel = blogCarouselService.getById(id);
        if (carousel != null) {
            return new ResponseResult<>(carousel);
        } else {
            // 你可以根据 ResultCodeEnum 定义更具体的错误码和消息
            return new ResponseResult<>(404, "轮播图未找到");
        }
    }

    /**
     * 添加轮播图
     * @param blogCarousel 轮播图数据 (从请求体中获取)
     * @return ResponseResult
     */
    @PostMapping
    public ResponseResult<?> addCarousel(@RequestBody BlogCarousel blogCarousel) {
        // 设置默认值或校验
        blogCarousel.setCreateTime(new Date());
        blogCarousel.setUpdateTime(new Date());
        if (blogCarousel.getClickCount() == null) {
            blogCarousel.setClickCount(0);
        }
        if (blogCarousel.getStatus() == null) {
            blogCarousel.setStatus(1); // 默认为启用
        }
        if (blogCarousel.getSort() == null) {
            blogCarousel.setSort(0); // 默认排序
        }
        boolean success = blogCarouselService.save(blogCarousel);
        return success ? new ResponseResult<>() : new ResponseResult<>(500, "添加失败");
    }

    /**
     * 更新轮播图
     * @param id 轮播图ID
     * @param blogCarousel 轮播图更新数据 (从请求体中获取)
     * @return ResponseResult
     */
    @PutMapping("/{id}")
    public ResponseResult<?> updateCarousel(@PathVariable Long id, @RequestBody BlogCarousel blogCarousel) {
        blogCarousel.setId(id); // 确保ID正确
        blogCarousel.setUpdateTime(new Date()); // 更新修改时间
        boolean success = blogCarouselService.updateById(blogCarousel);
        return success ? new ResponseResult<>() : new ResponseResult<>(500, "更新失败");
    }

    /**
     * 删除轮播图
     * @param id 轮播图ID
     * @return ResponseResult
     */
    @DeleteMapping("/{id}")
    public ResponseResult<?> deleteCarousel(@PathVariable Long id) {
        boolean success = blogCarouselService.removeById(id);
        return success ? new ResponseResult<>() : new ResponseResult<>(500, "删除失败");
    }

    /**
     * 更新轮播图状态 (启用/禁用)
     * @param id 轮播图ID
     * @param status 目标状态 (0:禁用, 1:启用)
     * @return ResponseResult
     */
    @PutMapping("/{id}/status")
    public ResponseResult<?> updateCarouselStatus(@PathVariable Long id, @RequestParam Integer status) {
        BlogCarousel carousel = new BlogCarousel();
        carousel.setId(id);
        carousel.setStatus(status);
        carousel.setUpdateTime(new Date());
        boolean success = blogCarouselService.updateById(carousel);
        return success ? new ResponseResult<>() : new ResponseResult<>(500, "更新状态失败");
    }

    /**
     * 记录轮播图点击次数
     * @param id 轮播图ID
     * @return ResponseResult
     */
    @PutMapping("/{id}/click")
    public ResponseResult<?> recordCarouselClick(@PathVariable Long id) {
        BlogCarousel carousel = blogCarouselService.getById(id);
        if (carousel == null) {
            return new ResponseResult<>(404, "轮播图未找到");
        }
        // 简单地增加点击次数
        carousel.setClickCount(carousel.getClickCount() == null ? 1 : carousel.getClickCount() + 1);
        carousel.setUpdateTime(new Date());
        boolean success = blogCarouselService.updateById(carousel);
        return success ? new ResponseResult<>() : new ResponseResult<>(500, "记录点击失败");
    }
}
