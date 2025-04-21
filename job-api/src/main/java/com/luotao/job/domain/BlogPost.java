package com.luotao.job.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.luotao.job.vo.UserInfoVo;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author luotao
 * @description 博客文章实体类
 */
@Data
@TableName("blog_post")
public class BlogPost {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
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
     * 作者ID
     */
    private Long authorId;

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