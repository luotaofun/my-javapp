package com.luotao.job.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 博客图片表
 * @TableName blog_image
 */
@TableName(value ="blog_image")
@Data
public class BlogImage implements Serializable {
    /**
     * 主键,自增长
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 文件的原始名称
     */
    @TableField(value = "filename")
    private String filename;

    /**
     * 文件在服务器上的存储路径
     */
    @TableField(value = "path")
    private String path;

    /**
     * 图片所属的博客文章标题,用于按文章组织图片
     */
    @TableField(value = "post_title")
    private String postTitle;

    /**
     * 图片的访问URL,用于在博客正文中引用
     */
    @TableField(value = "url")
    private String url;

    /**
     * 文件大小,单位字节,用于展示和限制上传大小
     */
    @TableField(value = "size")
    private Long size;

    /**
     * 图片宽度,单位像素,可选,用于展示
     */
    @TableField(value = "width")
    private Integer width;

    /**
     * 图片高度,单位像素,可选,用于展示
     */
    @TableField(value = "height")
    private Integer height;

    /**
     * 图片上传时间,默认为当前时间
     */
    @TableField(value = "upload_time")
    private Date uploadTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}