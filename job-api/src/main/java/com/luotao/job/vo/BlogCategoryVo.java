package com.luotao.job.vo;

import lombok.Data;

/**
 * @author luotao
 * @description 博客分类VO
 */
@Data
public class BlogCategoryVo {
    /**
     * 分类ID
     */
    private Long id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 分类描述
     */
    private String description;

    /**
     * 文章数量
     */
    private Integer postCount;
} 