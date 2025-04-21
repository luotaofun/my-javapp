package com.luotao.job.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luotao.job.domain.BlogPostTag;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

/**
 * @author luotao
 * @description 博客文章标签关联Mapper接口
 */
public interface BlogPostTagMapper extends BaseMapper<BlogPostTag> {
    /**
     * 删除文章的所有标签关联
     *
     * @param postId 文章ID
     * @return 影响行数
     */
    @Delete("DELETE FROM blog_post_tag WHERE post_id = #{postId}")
    int deleteByPostId(@Param("postId") Long postId);
} 