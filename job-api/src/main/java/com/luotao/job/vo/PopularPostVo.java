package com.luotao.job.vo;

import lombok.Data;

/**
 * @author luotao
 * @description 热门文章视图对象
 */
@Data
public class PopularPostVo {
    /**
     * 文章ID
     */
    private Long id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 浏览量
     */
    private Integer viewCount;
}