package com.luotao.job.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luotao.job.domain.BlogTag;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author luotao
 * @description 博客标签Mapper接口
 */
public interface BlogTagMapper extends BaseMapper<BlogTag> {
    /**
     * 查询文章的所有标签
     *
     * @param postId 文章ID
     * @return 标签列表
     */
    @Select("SELECT t.* FROM blog_tag t " +
            "INNER JOIN blog_post_tag pt ON t.id = pt.tag_id " +
            "WHERE pt.post_id = #{postId}")
    List<BlogTag> selectTagsByPostId(@Param("postId") Long postId);
    
    /**
     * 查询所有标签及其文章数量
     *
     * @return 标签列表
     */
    @Select("SELECT t.*, COUNT(pt.post_id) as post_count " +
            "FROM blog_tag t " +
            "LEFT JOIN blog_post_tag pt ON t.id = pt.tag_id " +
            "LEFT JOIN blog_post p ON pt.post_id = p.id AND p.status = 1 " +
            "GROUP BY t.id " +
            "ORDER BY post_count DESC, t.create_time DESC")
    List<BlogTag> selectTagWithCount();
} 