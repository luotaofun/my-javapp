package com.luotao.job.vo;

import lombok.Data;

/**
 * @author luotao
 * @description 博客标签VO
 */
@Data
public class BlogTagVo {
    /**
     * 标签ID
     */
    private Long id;

    /**
     * 标签名称
     */
    private String name;

    /**
     * 文章数量
     */
    private Integer postCount;
} 