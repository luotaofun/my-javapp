package com.luotao.job.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luotao.job.service.BlogPostService;
import com.luotao.job.utils.ResponseResult;
import com.luotao.job.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author luotao
 * @description 博客文章控制器
 */
@Slf4j
@RestController
@RequestMapping("/blog/posts")
@Api(tags = "博客文章管理接口")
public class BlogPostController {

    @Resource
    private BlogPostService blogPostService;

    /**
     * 创建博客文章
     */
    @PostMapping
    @ApiOperation("创建博客文章")
    public ResponseResult<Long> createPost(
            @ApiParam("用户ID") @RequestParam Long userId,
            @ApiParam("创建文章请求") @Valid @RequestBody BlogPostCreateVo createVo) {
        return blogPostService.createPost(userId, createVo);
    }

    /**
     * 更新博客文章
     */
    @PutMapping("/{postId}")
    @ApiOperation("更新博客文章")
    public ResponseResult<Void> updatePost(
            @ApiParam("用户ID") @RequestParam Long userId,
            @ApiParam("文章ID") @PathVariable Long postId,
            @ApiParam("更新文章请求") @Valid @RequestBody BlogPostUpdateVo updateVo) {
        return blogPostService.updatePost(userId, postId, updateVo);
    }

    /**
     * 获取博客文章详情
     */
    @GetMapping("/{postId}")
    @ApiOperation("获取博客文章详情")
    public ResponseResult<BlogPostDetailVo> getPostDetail(
            @ApiParam("文章ID") @PathVariable Long postId,
            @ApiParam("是否增加浏览量") @RequestParam(defaultValue = "true") boolean increaseViewCount) {
        return blogPostService.getPostDetail(postId, increaseViewCount);
    }

    /**
     * 分页查询博客文章列表
     */
    @GetMapping
    @ApiOperation("分页查询博客文章列表")
    public ResponseResult<Page<BlogPostListVo>> getPostList(BlogPostQueryVo queryVo) {
        return blogPostService.getPostList(queryVo);
    }

    /**
     * 删除博客文章
     */
    @DeleteMapping("/{postId}")
    @ApiOperation("删除博客文章")
    public ResponseResult<Void> deletePost(
            @ApiParam("用户ID") @RequestParam Long userId,
            @ApiParam("文章ID") @PathVariable Long postId) {
        return blogPostService.deletePost(userId, postId);
    }

    /**
     * 将文章移至回收站
     */
    @PutMapping("/{postId}/recycle")
    @ApiOperation("将文章移至回收站")
    public ResponseResult<Void> moveToRecycleBin(
            @ApiParam("用户ID") @RequestParam Long userId,
            @ApiParam("文章ID") @PathVariable Long postId) {
        return blogPostService.moveToRecycleBin(userId, postId);
    }

    /**
     * 从回收站恢复文章
     */
    @PutMapping("/{postId}/restore")
    @ApiOperation("从回收站恢复文章")
    public ResponseResult<Void> restoreFromRecycleBin(
            @ApiParam("用户ID") @RequestParam Long userId,
            @ApiParam("文章ID") @PathVariable Long postId) {
        return blogPostService.restoreFromRecycleBin(userId, postId);
    }

    /**
     * 点赞文章
     */
    @PostMapping("/{postId}/like")
    @ApiOperation("点赞文章")
    public ResponseResult<Void> likePost(@ApiParam("文章ID") @PathVariable Long postId) {
        return blogPostService.likePost(postId);
    }

    /**
     * 获取用户的文章列表
     */
    @GetMapping("/user/{userId}")
    @ApiOperation("获取用户的文章列表")
    public ResponseResult<List<BlogPostListVo>> getUserPosts(
            @ApiParam("用户ID") @PathVariable Long userId,
            @ApiParam("状态") @RequestParam(required = false) Integer status) {
        return blogPostService.getUserPosts(userId, status);
    }



    /**
     * 获取作者信息
     */
    @GetMapping("/author/{authorId}")
    @ApiOperation("获取作者信息")
    public ResponseResult<BlogAuthorVo> getAuthorInfo(
            @ApiParam("作者ID") @PathVariable Long authorId) {
        BlogAuthorVo authorInfo = blogPostService.getAuthorInfo(authorId);
        if (authorInfo == null) {
            return new ResponseResult<>(404, "作者不存在");
        }
        return new ResponseResult<>(200, "获取成功", authorInfo);
    }


} 