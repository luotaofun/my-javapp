package com.luotao.job.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luotao.job.domain.BlogPostTag;
import com.luotao.job.service.BlogPostTagService;
import com.luotao.job.mapper.BlogPostTagMapper;
import org.springframework.stereotype.Service;

/**
* @author T
* @description 针对表【blog_post_tag(博客文章标签关联表)】的数据库操作Service实现
* @createDate 2025-03-30 16:03:51
*/
@Service
public class BlogPostTagServiceImpl extends ServiceImpl<BlogPostTagMapper, BlogPostTag>
    implements BlogPostTagService{

}




