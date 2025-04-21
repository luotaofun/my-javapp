package com.luotao.job.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.luotao.job.domain.BlogTag;
import com.luotao.job.utils.ResponseResult;
import com.luotao.job.vo.BlogTagVo;

import java.util.List;

/**
 * @author luotao
 * @description 博客标签服务接口
 */
public interface BlogTagService extends IService<BlogTag> {
    /**
     * 获取所有标签列表
     *
     * @return 标签列表
     */
    ResponseResult<List<BlogTagVo>> getAllTags();
    
    /**
     * 创建标签
     *
     * @param name 标签名称
     * @return 创建结果
     */
    ResponseResult<Long> createTag(String name);
    
    /**
     * 更新标签
     *
     * @param tagId 标签ID
     * @param name 标签名称
     * @return 更新结果
     */
    ResponseResult<Void> updateTag(Long tagId, String name);
    
    /**
     * 删除标签
     *
     * @param tagId 标签ID
     * @return 删除结果
     */
    ResponseResult<Void> deleteTag(Long tagId);
    
    /**
     * 获取文章的标签列表
     *
     * @param postId 文章ID
     * @return 标签列表
     */
    ResponseResult<List<BlogTagVo>> getPostTags(Long postId);
}
