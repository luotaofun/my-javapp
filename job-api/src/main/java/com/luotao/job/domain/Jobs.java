package com.luotao.job.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 岗位表
 * @TableName jobs
 */
@TableName(value ="jobs")
@Data
public class Jobs implements Serializable {
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 岗位名称
     */
    @TableField(value = "job_title")
    private String jobTitle;

    /**
     * 岗位类别名称
     */
    @TableField(value = "category_name")
    private String categoryName;

    /**
     * 薪资范围
     */
    @TableField(value = "salary")
    private String salary;

    /**
     * 城市
     */
    @TableField(value = "city")
    private String city;

    /**
     * 岗位描述
     */
    @TableField(value = "job_desc")
    private String jobDesc;

    /**
     * 用人单位
     */
    @TableField(value = "company")
    private String company;

    /**
     * 企业类型,经营类型,规模
     */
    @TableField(value = "company_info")
    private String companyInfo;

    /**
     * 发布者
     */
    @TableField(value = "author")
    private String author;

    /**
     * 发布者头像
     */
    @TableField(value = "author_photo")
    private String authorPhoto;

    /**
     * 发布时间
     */
    @TableField(value = "publish_time")
    private String publishTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}