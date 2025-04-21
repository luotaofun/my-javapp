package com.luotao.job.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author luotao
 * @description 创建博客文章请求VO
 */
@Data
public class BlogPostCreateVo {
    /**
     * 文章标题
     */
    @NotBlank(message = "文章标题不能为空")
    @Size(max = 200, message = "文章标题不能超过200个字符")
    private String title;

    /**
     * 文章内容(Markdown格式)
     */
    @NotBlank(message = "文章内容不能为空")
    private String content;

    /**
     * 文章摘要
     */
    @Size(max = 500, message = "文章摘要不能超过500个字符")
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
     * 标签ID列表
     */
    private List<Long> tagIds;

    /**
     * 状态(0:草稿,1:已发布)
     */
    private Integer status;

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
} 