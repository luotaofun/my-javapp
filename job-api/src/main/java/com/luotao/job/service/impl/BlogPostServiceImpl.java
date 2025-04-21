package com.luotao.job.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luotao.job.domain.BlogPost;
import com.luotao.job.domain.BlogPostTag;
import com.luotao.job.domain.BlogTag;
import com.luotao.job.domain.User;
import com.luotao.job.mapper.BlogPostMapper;
import com.luotao.job.mapper.BlogPostTagMapper;
import com.luotao.job.mapper.BlogTagMapper;
import com.luotao.job.mapper.UserMapper;
import com.luotao.job.service.BlogPostService;
import com.luotao.job.utils.ResponseResult;
import com.luotao.job.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author luotao
 * @description 博客文章服务实现类
 */
@Slf4j
@Service
public class BlogPostServiceImpl extends ServiceImpl<BlogPostMapper, BlogPost> implements BlogPostService {

    @Resource
    private BlogPostTagMapper blogPostTagMapper;

    @Resource
    private BlogTagMapper blogTagMapper;

    @Resource
    private UserMapper userMapper;
    @Resource
    private BlogPostMapper blogMapper;

    /**
     * 创建博客文章
     *
     * @param userId   用户ID
     * @param createVo 创建文章请求
     * @return 创建结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Long> createPost(Long userId, BlogPostCreateVo createVo) {
        // 1. 检查用户是否存在
        User user = userMapper.selectById(userId);
        if (user == null) {
            return new ResponseResult<>(400, "用户不存在");
        }

        // 2. 构建博客文章对象
        BlogPost blogPost = new BlogPost();
        BeanUtils.copyProperties(createVo, blogPost);

        // 3. 设置作者ID和默认值
        blogPost.setAuthorId(userId);
        blogPost.setViewCount(0);
        blogPost.setCommentCount(0);
        blogPost.setLikeCount(0);

        // 4. 如果摘要为空，自动生成摘要
        if (!StringUtils.hasText(blogPost.getSummary()) && StringUtils.hasText(blogPost.getContent())) {
            // 从内容中提取前100个字符作为摘要
            String content = blogPost.getContent();
            // 去除Markdown标记
            content = content.replaceAll("#+\\s*", "")  // 标题
                    .replaceAll("!?\\[.*?\\]\\(.*?\\)", "") // 链接和图片
                    .replaceAll("\\*\\*|__", "") // 粗体
                    .replaceAll("\\*|_", "")     // 斜体
                    .replaceAll("`.*?`", "")     // 行内代码
                    .replaceAll("```[\\s\\S]*?```", "") // 代码块
                    .replaceAll("\\n", " ");     // 换行

            blogPost.setSummary(content.length() > 100 ? content.substring(0, 100) + "..." : content);
        }

        // 5. 保存文章
        save(blogPost);
        Long postId = blogPost.getId();

        // 6. 处理标签关联
        if (createVo.getTagIds() != null && !createVo.getTagIds().isEmpty()) {
            List<BlogPostTag> postTags = createVo.getTagIds().stream()
                    .map(tagId -> {
                        BlogPostTag postTag = new BlogPostTag();
                        postTag.setPostId(postId);
                        postTag.setTagId(tagId);
                        return postTag;
                    })
                    .collect(Collectors.toList());

            // 批量插入标签关联
            for (BlogPostTag postTag : postTags) {
                blogPostTagMapper.insert(postTag);
            }
        }

        return new ResponseResult<>(200, "创建成功", postId);
    }

    /**
     * 更新博客文章
     *
     * @param userId   用户ID
     * @param postId   文章ID
     * @param updateVo 更新文章请求
     * @return 更新结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Void> updatePost(Long userId, Long postId, BlogPostUpdateVo updateVo) {
        // 1. 检查文章是否存在
        BlogPost blogPost = getById(postId);
        if (blogPost == null) {
            return new ResponseResult<>(404, "文章不存在");
        }

        // 2. 检查权限（只有作者或管理员可以修改）
        User user = userMapper.selectById(userId);
        if (user == null) {
            return new ResponseResult<>(400, "用户不存在");
        }

        if (!blogPost.getAuthorId().equals(userId) && !"ADMIN".equals(user.getRole())) {
            return new ResponseResult<>(403, "无权限修改此文章");
        }

        // 3. 更新文章基本信息
        boolean needUpdate = false;

        if (StringUtils.hasText(updateVo.getTitle())) {
            blogPost.setTitle(updateVo.getTitle());
            needUpdate = true;
        }

        if (updateVo.getContent() != null) {
            blogPost.setContent(updateVo.getContent());
            needUpdate = true;

            // 如果内容更新了，且摘要为空或也要更新，则重新生成摘要
            if ((!StringUtils.hasText(blogPost.getSummary()) || StringUtils.hasText(updateVo.getSummary()))
                    && StringUtils.hasText(updateVo.getContent())) {
                // 从内容中提取前100个字符作为摘要
                String content = updateVo.getContent();
                // 去除Markdown标记
                content = content.replaceAll("#+\\s*", "")  // 标题
                        .replaceAll("!?\\[.*?\\]\\(.*?\\)", "") // 链接和图片
                        .replaceAll("\\*\\*|__", "") // 粗体
                        .replaceAll("\\*|_", "")     // 斜体
                        .replaceAll("`.*?`", "")     // 行内代码
                        .replaceAll("```[\\s\\S]*?```", "") // 代码块
                        .replaceAll("\\n", " ");     // 换行

                blogPost.setSummary(content.length() > 100 ? content.substring(0, 100) + "..." : content);
            }
        }

        if (StringUtils.hasText(updateVo.getSummary())) {
            blogPost.setSummary(updateVo.getSummary());
            needUpdate = true;
        }

        if (updateVo.getCoverImage() != null) {
            blogPost.setCoverImage(updateVo.getCoverImage());
            needUpdate = true;
        }

        if (updateVo.getCategoryId() != null) {
            blogPost.setCategoryId(updateVo.getCategoryId());
            needUpdate = true;
        }

        if (updateVo.getStatus() != null) {
            blogPost.setStatus(updateVo.getStatus());
            needUpdate = true;
        }

        if (updateVo.getIsTop() != null) {
            blogPost.setIsTop(updateVo.getIsTop());
            needUpdate = true;
        }

        if (updateVo.getIsOriginal() != null) {
            blogPost.setIsOriginal(updateVo.getIsOriginal());
            needUpdate = true;
        }

        if (updateVo.getSourceUrl() != null) {
            blogPost.setSourceUrl(updateVo.getSourceUrl());
            needUpdate = true;
        }

        // 4. 更新文章
        if (needUpdate) {
            updateById(blogPost);
        }

        // 5. 处理标签关联
        if (updateVo.getTagIds() != null) {
            // 先删除原有标签关联
            blogPostTagMapper.deleteByPostId(postId);

            // 添加新的标签关联
            if (!updateVo.getTagIds().isEmpty()) {
                List<BlogPostTag> postTags = updateVo.getTagIds().stream()
                        .map(tagId -> {
                            BlogPostTag postTag = new BlogPostTag();
                            postTag.setPostId(postId);
                            postTag.setTagId(tagId);
                            return postTag;
                        })
                        .collect(Collectors.toList());

                // 批量插入标签关联
                for (BlogPostTag postTag : postTags) {
                    blogPostTagMapper.insert(postTag);
                }
            }
        }

        return new ResponseResult<>(200, "更新成功");
    }

    /**
     * 获取博客文章详情
     *
     * @param postId            文章ID
     * @param increaseViewCount 是否增加浏览量
     * @return 文章详情
     */
    @Override
    public ResponseResult<BlogPostDetailVo> getPostDetail(Long postId, boolean increaseViewCount) {
        // 1. 查询文章
        BlogPost blogPost = getById(postId);
        if (blogPost == null) {
            return new ResponseResult<>(404, "文章不存在");
        }

        // 2. 增加浏览量
        if (increaseViewCount) {
            baseMapper.incrementViewCount(postId);
            blogPost.setViewCount(blogPost.getViewCount() + 1);
        }

        // 3. 查询作者信息
        User author = userMapper.selectById(blogPost.getAuthorId());

        // 4. 查询标签信息
        List<BlogTag> tags = blogTagMapper.selectTagsByPostId(postId);
        List<BlogTagVo> tagVos = tags.stream().map(tag -> {
            BlogTagVo tagVo = new BlogTagVo();
            tagVo.setId(tag.getId());
            tagVo.setName(tag.getName());
            return tagVo;
        }).collect(Collectors.toList());

        // 5. 构建详情VO
        BlogPostDetailVo detailVo = new BlogPostDetailVo();
        BeanUtils.copyProperties(blogPost, detailVo);

        // 设置作者信息
        if (author != null) {
            detailVo.setAuthorName(author.getUsername());
            detailVo.setAuthorAvatar(author.getAvatar());
        }

        // 设置标签信息
        detailVo.setTags(tagVos);

        return new ResponseResult<>(200, "获取成功", detailVo);
    }

    /**
     * 分页查询博客文章列表
     *
     * @param queryVo 查询参数
     * @return 分页结果
     */
    @Override
    public ResponseResult<Page<BlogPostListVo>> getPostList(BlogPostQueryVo queryVo) {
        // 1. 创建分页对象
        Page<BlogPostListVo> page = new Page<>(queryVo.getPage(), queryVo.getSize());

        // 2. 调用Mapper查询
        IPage<BlogPostListVo> postPage = baseMapper.selectPostList(
                page,
                queryVo.getCategoryId(),
                queryVo.getTagId(),
                queryVo.getKeyword(),
                queryVo.getStatus(),
                queryVo.getTitle(),
                queryVo.getAuthorId()
        );

        return new ResponseResult<>(200, "获取成功", (Page<BlogPostListVo>) postPage);
    }

    /**
     * 删除博客文章
     *
     * @param userId 用户ID
     * @param postId 文章ID
     * @return 删除结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Void> deletePost(Long userId, Long postId) {
        // 1. 检查文章是否存在
        BlogPost blogPost = getById(postId);
        if (blogPost == null) {
            return new ResponseResult<>(404, "文章不存在");
        }

        // 2. 检查权限（只有作者或管理员可以删除）
        User user = userMapper.selectById(userId);
        if (user == null) {
            return new ResponseResult<>(400, "用户不存在");
        }

        if (!blogPost.getAuthorId().equals(userId) && !"ADMIN".equals(user.getRole())) {
            return new ResponseResult<>(403, "无权限删除此文章");
        }

        // 3. 删除标签关联
        blogPostTagMapper.deleteByPostId(postId);

        // 4. 删除文章
        removeById(postId);

        return new ResponseResult<>(200, "删除成功");
    }

    /**
     * 将文章移至回收站
     *
     * @param userId 用户ID
     * @param postId 文章ID
     * @return 操作结果
     */
    @Override
    public ResponseResult<Void> moveToRecycleBin(Long userId, Long postId) {
        // 1. 检查文章是否存在
        BlogPost blogPost = getById(postId);
        if (blogPost == null) {
            return new ResponseResult<>(404, "文章不存在");
        }

        // 2. 检查权限（只有作者或管理员可以操作）
        User user = userMapper.selectById(userId);
        if (user == null) {
            return new ResponseResult<>(400, "用户不存在");
        }

        if (!blogPost.getAuthorId().equals(userId) && !"ADMIN".equals(user.getRole())) {
            return new ResponseResult<>(403, "无权限操作此文章");
        }

        // 3. 更新状态为回收站(2)
        blogPost.setStatus(2);
        updateById(blogPost);

        return new ResponseResult<>(200, "已移至回收站");
    }

    /**
     * 从回收站恢复文章
     *
     * @param userId 用户ID
     * @param postId 文章ID
     * @return 操作结果
     */
    @Override
    public ResponseResult<Void> restoreFromRecycleBin(Long userId, Long postId) {
        // 1. 检查文章是否存在
        BlogPost blogPost = getById(postId);
        if (blogPost == null) {
            return new ResponseResult<>(404, "文章不存在");
        }

        // 2. 检查文章是否在回收站
        if (blogPost.getStatus() != 2) {
            return new ResponseResult<>(400, "文章不在回收站");
        }

        // 3. 检查权限（只有作者或管理员可以操作）
        User user = userMapper.selectById(userId);
        if (user == null) {
            return new ResponseResult<>(400, "用户不存在");
        }

        if (!blogPost.getAuthorId().equals(userId) && !"ADMIN".equals(user.getRole())) {
            return new ResponseResult<>(403, "无权限操作此文章");
        }

        // 4. 恢复为草稿状态(0)
        blogPost.setStatus(0);
        updateById(blogPost);

        return new ResponseResult<>(200, "已从回收站恢复");
    }

    /**
     * 点赞文章
     *
     * @param postId 文章ID
     * @return 操作结果
     */
    @Override
    public ResponseResult<Void> likePost(Long postId) {
        // 1. 检查文章是否存在
        BlogPost blogPost = getById(postId);
        if (blogPost == null) {
            return new ResponseResult<>(404, "文章不存在");
        }

        // 2. 增加点赞数
        baseMapper.incrementLikeCount(postId);

        return new ResponseResult<>(200, "点赞成功");
    }

    /**
     * 获取用户的文章列表
     *
     * @param userId 用户ID
     * @param status 状态
     * @return 文章列表
     */
    @Override
    public ResponseResult<List<BlogPostListVo>> getUserPosts(Long userId, Integer status) {
        // 1. 检查用户是否存在
        User user = userMapper.selectById(userId);
        if (user == null) {
            return new ResponseResult<>(400, "用户不存在");
        }

        // 2. 创建查询对象
        BlogPostQueryVo queryVo = new BlogPostQueryVo();
        queryVo.setAuthorId(userId);
        queryVo.setStatus(status);
        queryVo.setPage(1);
        queryVo.setSize(Integer.MAX_VALUE); // 获取所有文章

        // 3. 查询文章列表
        ResponseResult<Page<BlogPostListVo>> result = getPostList(queryVo);

        if (result.getCode() != 200) {
            return new ResponseResult<>(result.getCode(), result.getMsg());
        }

//        return new ResponseResult<>(200, "获取成功", result.getData().getRecords());
        return new ResponseResult(200, "获取成功", result.getData());
    }

    // 在创建或更新文章前处理内容
    private BlogPost preprocessBlogPost(BlogPost blogPost) {
        // 如果内容过长，可以进行压缩或其他处理
        if (blogPost.getContent() != null && blogPost.getContent().length() > 65000) {
            // 可以考虑压缩或分段存储
            log.warn("文章内容过长: {}", blogPost.getTitle());
        }

        // 处理封面图片URL
        if (blogPost.getCoverImage() != null && blogPost.getCoverImage().length() > 1000) {
            // 可以考虑将长URL存储为短URL
            log.warn("封面图片URL过长: {}", blogPost.getTitle());
        }

        return blogPost;
    }

    @Override
    public BlogAuthorVo getAuthorInfo(Long authorId) {
        // 获取基本作者信息
        BlogAuthorVo author = blogMapper.getAuthorInfo(authorId);

        if (author != null) {
            // 获取统计信息
            Map<String, Object> statistics = blogMapper.getAuthorStatistics(authorId);
            if (statistics != null) {
                // 设置统计信息
                author.setArticleCount((Long) statistics.get("articleCount"));
                author.setCategoryCount((Long) statistics.get("categoryCount"));
                author.setTagCount((Long) statistics.get("tagCount"));
                author.setTotalViews((Long) statistics.get("totalViews"));
                author.setTotalComments((Long) statistics.get("totalComments"));
                author.setTotalLikes((Long) statistics.get("totalLikes"));
                author.setLastPostTime((java.time.LocalDateTime) statistics.get("lastPostTime"));
            }
        }

        return author;
    }

    @Override
    public List<BlogPost> getAuthorPosts(Long authorId, int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        return blogMapper.getAuthorPosts(authorId, offset, pageSize);
    }

    @Override
    public Long countAuthorPosts(Long authorId) {
        return blogMapper.countAuthorPosts(authorId);
    }


}




