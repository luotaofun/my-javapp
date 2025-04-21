package com.luotao.job.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luotao.job.domain.BlogCategory;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author luotao
 * @description 博客分类Mapper接口
 */
public interface BlogCategoryMapper extends BaseMapper<BlogCategory> {
    /**
     * 查询所有分类及其文章数量
     *
     * @return 分类列表
     */
    @Select("SELECT c.*, COUNT(p.id) as post_count " +
            "FROM blog_category c " +
            "LEFT JOIN blog_post p ON c.id = p.category_id AND p.status = 1 " +
            "GROUP BY c.id " +
            "ORDER BY c.sort ASC, c.create_time DESC")
    List<BlogCategory> selectCategoryWithCount();
} 