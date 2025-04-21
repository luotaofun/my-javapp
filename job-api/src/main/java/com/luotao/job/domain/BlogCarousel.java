package com.luotao.job.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 轮播图表
 * @TableName blog_carousel
 */
@TableName(value ="blog_carousel")
@Data
public class BlogCarousel implements Serializable {
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 轮播图标题
     */
    @TableField(value = "title")
    private String title;
    /**
     * 轮播图类型
     */
    @TableField(value = "type")
    private String type;
    /**
     * 轮播图描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 轮播图图片URL
     */
    @TableField(value = "image_url")
    private String imageUrl;

    /**
     * 轮播图跳转链接
     */
    @TableField(value = "link_url")
    private String linkUrl;

    /**
     * 轮播图排序（数字越小越靠前）
     */
    @TableField(value = "sort")
    private Integer sort;

    /**
     * 轮播图状态（0:禁用, 1:启用）
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 跳转目标类型（如：文章、活动、外部链接等）
     */
    @TableField(value = "target_type")
    private String targetType;

    /**
     * 跳转目标ID（如文章ID、活动ID等）
     */
    @TableField(value = "target_id")
    private Long targetId;

    /**
     * 语言（如：zh-CN, en-US等）
     */
    @TableField(value = "language")
    private String language;

    /**
     * 轮播图开始展示时间
     */
    @TableField(value = "start_time")
    private Date startTime;

    /**
     * 轮播图结束展示时间
     */
    @TableField(value = "end_time")
    private Date endTime;

    /**
     * 图片宽度（单位：像素）
     */
    @TableField(value = "width")
    private Integer width;

    /**
     * 图片高度（单位：像素）
     */
    @TableField(value = "height")
    private Integer height;

    /**
     * 点击次数
     */
    @TableField(value = "click_count")
    private Integer clickCount;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}