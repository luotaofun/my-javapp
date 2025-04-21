package com.luotao.job.vo;

import lombok.Data;

/**
 * @author luotao
 * @description 创建博客分类请求VO
 */
@Data
public class BlogCategoryCreateVo {
    /**
     * 分类名称
     */
    private String name;

    /**
     * 分类描述
     */
    private String description;

    /**
     * 排序
     */
    private Integer sort = 0;
} 