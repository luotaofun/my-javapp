package com.luotao.job.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luotao.job.domain.BlogPost;
import com.luotao.job.vo.BlogAuthorVo;
import com.luotao.job.vo.BlogPostListVo;
import com.luotao.job.vo.PopularPostVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * @author luotao
 * @description 博客文章Mapper接口
 */
public interface BlogPostMapper extends BaseMapper<BlogPost> {
    /**
     * 分页查询文章列表
     *
     * @param page 分页参数
     * @param categoryId 分类ID
     * @param tagId 标签ID
     * @param keyword 关键词
     * @param status 状态
     * @return 分页结果
     */
    IPage<BlogPostListVo> selectPostList(Page<BlogPostListVo> page, 
                                         @Param("categoryId") Long categoryId,
                                         @Param("tagId") Long tagId,
                                         @Param("keyword") String keyword,
                                         @Param("status") Integer status,
                                         @Param("title") String title,
                                         @Param("authorId") Long authorId);
    
    /**
     * 增加文章浏览量
     *
     * @param postId 文章ID
     * @return 影响行数
     */
    @Update("UPDATE blog_post SET view_count = view_count + 1 WHERE id = #{postId}")
    int incrementViewCount(@Param("postId") Long postId);
    
    /**
     * 增加文章点赞数
     *
     * @param postId 文章ID
     * @return 影响行数
     */
    @Update("UPDATE blog_post SET like_count = like_count + 1 WHERE id = #{postId}")
    int incrementLikeCount(@Param("postId") Long postId);
    
    /**
     * 增加文章评论数
     *
     * @param postId 文章ID
     * @return 影响行数
     */
    @Update("UPDATE blog_post SET comment_count = comment_count + 1 WHERE id = #{postId}")
    int incrementCommentCount(@Param("postId") Long postId);




    /**
     * 获取作者信息
     * @param authorId 作者ID
     * @return 作者信息
     */
    BlogAuthorVo getAuthorInfo(@Param("authorId") Long authorId);

    /**
     * 获取作者的文章统计信息
     * @param authorId 作者ID
     * @return 统计信息
     */
    Map<String, Object> getAuthorStatistics(@Param("authorId") Long authorId);

    /**
     * 获取作者的博客文章列表
     * @param authorId 作者ID
     * @param offset 偏移量
     * @param pageSize 每页大小
     * @return 文章列表
     */
    List<BlogPost> getAuthorPosts(@Param("authorId") Long authorId,
                                  @Param("offset") int offset,
                                  @Param("pageSize") int pageSize);

    /**
     * 获取作者的文章总数
     * @param authorId 作者ID
     * @return 文章总数
     */
    Long countAuthorPosts(@Param("authorId") Long authorId);


} 