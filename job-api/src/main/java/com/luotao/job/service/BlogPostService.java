package com.luotao.job.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.luotao.job.domain.BlogPost;
import com.luotao.job.utils.ResponseResult;
import com.luotao.job.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author T
* @description 针对表【blog_post(博客文章表)】的数据库操作Service
* @createDate 2025-03-30 16:02:34
 * MyBatis-Plus 会根据泛型参数自动关联对应的实体类和 Mapper 接口。
*/
public interface BlogPostService extends IService<BlogPost> {
    /**
     * 创建博客文章
     *
     * @param userId 用户ID
     * @param createVo 创建文章请求
     * @return 创建结果
     */
    ResponseResult<Long> createPost(Long userId, BlogPostCreateVo createVo);

    /**
     * 更新博客文章
     *
     * @param userId 用户ID
     * @param postId 文章ID
     * @param updateVo 更新文章请求
     * @return 更新结果
     */
    ResponseResult<Void> updatePost(Long userId, Long postId, BlogPostUpdateVo updateVo);

    /**
     * 获取博客文章详情
     *
     * @param postId 文章ID
     * @param increaseViewCount 是否增加浏览量
     * @return 文章详情
     */
    ResponseResult<BlogPostDetailVo> getPostDetail(Long postId, boolean increaseViewCount);

    /**
     * 分页查询博客文章列表
     *
     * @param queryVo 查询参数
     * @return 分页结果
     */
    ResponseResult<Page<BlogPostListVo>> getPostList(BlogPostQueryVo queryVo);

    /**
     * 删除博客文章
     *
     * @param userId 用户ID
     * @param postId 文章ID
     * @return 删除结果
     */
    ResponseResult<Void> deletePost(Long userId, Long postId);

    /**
     * 将文章移至回收站
     *
     * @param userId 用户ID
     * @param postId 文章ID
     * @return 操作结果
     */
    ResponseResult<Void> moveToRecycleBin(Long userId, Long postId);

    /**
     * 从回收站恢复文章
     *
     * @param userId 用户ID
     * @param postId 文章ID
     * @return 操作结果
     */
    ResponseResult<Void> restoreFromRecycleBin(Long userId, Long postId);
    
    /**
     * 点赞文章
     *
     * @param postId 文章ID
     * @return 操作结果
     */
    ResponseResult<Void> likePost(Long postId);
    
    /**
     * 获取用户的文章列表
     *
     * @param userId 用户ID
     * @param status 状态
     * @return 文章列表
     */
    ResponseResult<List<BlogPostListVo>> getUserPosts(Long userId, Integer status);

    /**
     * 获取作者信息
     * @param authorId 作者ID
     * @return 作者信息
     */
    BlogAuthorVo getAuthorInfo(Long authorId);
    /**
     * 获取作者的博客文章列表
     * @param authorId 作者ID
     * @param pageSize 每页大小
     * @return 文章列表
     */
    List<BlogPost> getAuthorPosts(Long authorId, int page, int pageSize);
    /**
     * 获取作者的文章总数
     * @param authorId 作者ID
     * @return 文章总数
     */
    Long countAuthorPosts(Long authorId);


}
