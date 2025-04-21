package com.luotao.job.vo;

import lombok.Data;
import java.time.LocalDateTime;

/**
* @Description: 获取作者信息Vo
* @Author: LuoTao
* @Date: 2025-04-04 15:23:35
**/
@Data
public class BlogAuthorVo {
    private Long id; // 作者id
    private String title;
    private String username;
    private String nickname;
    private String email;
    private String avatar;
    private Integer status; //用户禁用状态
    private String role; // 角色
    private String description; // 作者描述
    private LocalDateTime lastLogin; // 最后登录时间
    private LocalDateTime createTime; // 创建时间
    private String socialLinks;

    // 统计信息 - 这些字段不在数据库中，需要通过查询计算
    private Long articleCount; // 文章统计
    private Long categoryCount;// 分类统计
    private Long tagCount; // 标签统计
    private Long totalViews; //浏览量统计
    private Long totalComments; // 评论量统计
    private Long totalLikes; // 点赞量统计
    private LocalDateTime lastPostTime; // 最新提交时间
}