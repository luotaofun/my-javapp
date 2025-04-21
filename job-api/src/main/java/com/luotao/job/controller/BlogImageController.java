package com.luotao.job.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.luotao.job.domain.BlogImage;
import com.luotao.job.service.BlogImageService;
import com.luotao.job.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 博客图片控制器
 */
@RestController
@RequestMapping("/blog/images")
@Slf4j
@Api(tags = "博客图片管理")
public class BlogImageController {
    @Autowired
    private BlogImageService blogImageService;

    /**
     * 分页查询图片列表
     */
    @GetMapping
    @ApiOperation("分页查询图片列表")
    public ResponseResult listImages(
            @RequestParam(required = false) String postTitle,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        IPage<BlogImage> blogImageIPage = blogImageService.listImages(postTitle, page, size);
        log.info("总行数:{}",blogImageIPage.getTotal());
        log.info("总页数:{}",blogImageIPage.getPages());
        log.info("当前页:{}",blogImageIPage.getCurrent());
        return  new ResponseResult(blogImageIPage);
    }

    /**
     * 删除图片
     */
    @DeleteMapping("/{imageId}")
    @ApiOperation("删除图片")
    public ResponseResult deleteImage(@PathVariable Long imageId) {
        return blogImageService.deleteImage(imageId);
    }

    /**
     * 重命名图片
     */
    @PutMapping("/{imageId}")
    @ApiOperation("重命名图片")
    public ResponseResult renameImage(
            @PathVariable Long imageId,
            @RequestParam String newName) {
        return blogImageService.renameImage(imageId, newName);
    }
} 