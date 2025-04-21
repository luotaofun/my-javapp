package com.luotao.job.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author luotao
 * @description 博客文章标签关联实体类
 */
@Data
@TableName("blog_post_tag")
public class BlogPostTag {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 文章ID
     */
    private Long postId;

    /**
     * 标签ID
     */
    private Long tagId;
} 