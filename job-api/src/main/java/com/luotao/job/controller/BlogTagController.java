package com.luotao.job.controller;

import com.luotao.job.service.BlogTagService;
import com.luotao.job.utils.ResponseResult;
import com.luotao.job.vo.BlogTagCreateVo;
import com.luotao.job.vo.BlogTagVo;
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
 * @description 博客标签控制器
 */
@Slf4j
@RestController
@RequestMapping("/blog/tags")
@Api(tags = "博客标签管理接口")
public class BlogTagController {

    @Resource
    private BlogTagService blogTagService;

    /**
     * 获取所有标签列表
     */
    @GetMapping
    @ApiOperation("获取所有标签列表")
    public ResponseResult<List<BlogTagVo>> getAllTags() {
        return blogTagService.getAllTags();
    }

    /**
     * 创建标签
     */
    @PostMapping
    @ApiOperation("创建标签")
    public ResponseResult<Long> createTag(@RequestBody BlogTagCreateVo tagVo) {
        try {
            log.info("创建标签，参数：{}", tagVo);
            
            if (!StringUtils.hasText(tagVo.getName())) {
                return new ResponseResult<>(400, "标签名称不能为空");
            }
            
            return blogTagService.createTag(tagVo.getName());
        } catch (Exception e) {
            log.error("创建标签失败: ", e);
            return new ResponseResult<>(500, "创建标签失败");
        }
    }

    /**
     * 更新标签
     */
    @PutMapping("/{tagId}")
    @ApiOperation("更新标签")
    public ResponseResult<Void> updateTag(
            @ApiParam("标签ID") @PathVariable Long tagId,
            @ApiParam("标签名称") @RequestParam String name) {
        return blogTagService.updateTag(tagId, name);
    }

    /**
     * 删除标签
     */
    @DeleteMapping("/{tagId}")
    @ApiOperation("删除标签")
    public ResponseResult<Void> deleteTag(
            @ApiParam("标签ID") @PathVariable Long tagId) {
        return blogTagService.deleteTag(tagId);
    }

    /**
     * 获取文章的标签列表
     */
    @GetMapping("/post/{postId}")
    @ApiOperation("获取文章的标签列表")
    public ResponseResult<List<BlogTagVo>> getPostTags(
            @ApiParam("文章ID") @PathVariable Long postId) {
        return blogTagService.getPostTags(postId);
    }
} 