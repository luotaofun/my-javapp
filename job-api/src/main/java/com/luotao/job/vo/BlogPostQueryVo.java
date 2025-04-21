package com.luotao.job.vo;

import lombok.Data;

/**
 * @author luotao
 * @description 博客文章查询参数VO
 */
@Data
public class BlogPostQueryVo {
    /**
     * 页码
     */
    private Integer page = 1;

    /**
     * 每页大小
     */
    private Integer size = 10;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 标签ID
     */
    private Long tagId;

    /**
     * 关键词
     */
    private String keyword;

    /**
     * 状态(0:草稿,1:已发布,2:回收站)
     */
    private Integer status;

    /**
     * 作者ID
     */
    private Long authorId;

    /**
     * 文章标题
     */
    private String title;
} 