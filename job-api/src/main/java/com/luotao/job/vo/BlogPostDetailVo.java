package com.luotao.job.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author luotao
 * @description 博客文章详情响应VO
 */
@Data
public class BlogPostDetailVo {
    /**
     * 文章ID
     */
    private Long id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章内容(Markdown格式)
     */
    private String content;

    /**
     * 文章摘要
     */
    private String summary;

    /**
     * 封面图片URL
     */
    private String coverImage;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 标签列表
     */
    private List<BlogTagVo> tags;

    /**
     * 作者ID
     */
    private Long authorId;

    /**
     * 作者名称
     */
    private String authorName;

    /**
     * 作者头像
     */
    private String authorAvatar;

    /**
     * 状态(0:草稿,1:已发布,2:回收站)
     */
    private Integer status;

    /**
     * 浏览量
     */
    private Integer viewCount;

    /**
     * 评论数
     */
    private Integer commentCount;

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 是否置顶(0:否,1:是)
     */
    private Boolean isTop;

    /**
     * 是否原创(0:否,1:是)
     */
    private Boolean isOriginal;

    /**
     * 原文链接(非原创时)
     */
    private String sourceUrl;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
} 