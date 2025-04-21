package com.luotao.job.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luotao.job.domain.BlogPostTag;
import com.luotao.job.domain.BlogTag;
import com.luotao.job.mapper.BlogPostTagMapper;
import com.luotao.job.mapper.BlogTagMapper;
import com.luotao.job.service.BlogTagService;
import com.luotao.job.utils.ResponseResult;
import com.luotao.job.vo.BlogTagVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author luotao
 * @description 博客标签服务实现类
 */
@Slf4j
@Service
public class BlogTagServiceImpl extends ServiceImpl<BlogTagMapper, BlogTag> implements BlogTagService {

    @Resource
    private BlogPostTagMapper blogPostTagMapper;

    /**
     * 获取所有标签列表
     *
     * @return 标签列表
     */
    @Override
    public ResponseResult<List<BlogTagVo>> getAllTags() {
        // 1. 查询所有标签及其文章数量
        List<BlogTag> tags = baseMapper.selectTagWithCount();
        
        // 2. 转换为VO对象
        List<BlogTagVo> tagVos = tags.stream().map(tag -> {
            BlogTagVo tagVo = new BlogTagVo();
            tagVo.setId(tag.getId());
            tagVo.setName(tag.getName());
            
            // 查询使用该标签的文章数量
            LambdaQueryWrapper<BlogPostTag> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(BlogPostTag::getTagId, tag.getId());
//            int count = blogPostTagMapper.selectCount(queryWrapper);
            Long countLong = blogPostTagMapper.selectCount(queryWrapper);
            int count = (countLong == null) ? 0 : countLong.intValue();
            tagVo.setPostCount(count);
            
            return tagVo;
        }).collect(Collectors.toList());
        
        return new ResponseResult<>(200, "获取成功", tagVos);
    }

    /**
     * 创建标签
     *
     * @param name 标签名称
     * @return 创建结果
     */
    @Override
    public ResponseResult<Long> createTag(String name) {
        try {
            // 1. 检查标签名称是否已存在
            LambdaQueryWrapper<BlogTag> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(BlogTag::getName, name);
            if (this.count(queryWrapper) > 0) {
                return new ResponseResult<>(400, "标签名称已存在");
            }

            // 2. 创建标签
            BlogTag tag = new BlogTag();
            tag.setName(name);
            tag.setCreateTime(LocalDateTime.now());
            
            // 3. 保存标签
            this.save(tag);

            return new ResponseResult<>(200, "创建成功", tag.getId());
        } catch (Exception e) {
            log.error("创建标签失败: ", e);
            return new ResponseResult<>(500, "创建标签失败");
        }
    }

    /**
     * 更新标签
     *
     * @param tagId 标签ID
     * @param name  标签名称
     * @return 更新结果
     */
    @Override
    public ResponseResult<Void> updateTag(Long tagId, String name) {
        // 1. 检查标签是否存在
        BlogTag tag = getById(tagId);
        if (tag == null) {
            return new ResponseResult<>(404, "标签不存在");
        }
        
        // 2. 检查标签名称是否为空
        if (!StringUtils.hasText(name)) {
            return new ResponseResult<>(400, "标签名称不能为空");
        }
        
        // 3. 如果修改了名称，检查名称是否已存在
        if (!name.equals(tag.getName())) {
            LambdaQueryWrapper<BlogTag> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(BlogTag::getName, name);
            if (baseMapper.selectCount(queryWrapper) > 0) {
                return new ResponseResult<>(400, "标签名称已存在");
            }
        }
        
        // 4. 更新标签
        tag.setName(name);
        updateById(tag);
        
        return new ResponseResult<>(200, "更新成功");
    }

    /**
     * 删除标签
     *
     * @param tagId 标签ID
     * @return 删除结果
     */
    @Override
    public ResponseResult<Void> deleteTag(Long tagId) {
        // 1. 检查标签是否存在
        BlogTag tag = getById(tagId);
        if (tag == null) {
            return new ResponseResult<>(404, "标签不存在");
        }
        
        // 2. 检查是否有文章使用该标签
        LambdaQueryWrapper<BlogPostTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BlogPostTag::getTagId, tagId);
//        int count = blogPostTagMapper.selectCount(queryWrapper);
        Long countLong = blogPostTagMapper.selectCount(queryWrapper);
        int count = (countLong == null) ? 0 : countLong.intValue();
        
        if (count > 0) {
            return new ResponseResult<>(400, "该标签下有" + count + "篇文章，无法删除");
        }
        
        // 3. 删除标签
        removeById(tagId);
        
        return new ResponseResult<>(200, "删除成功");
    }

    /**
     * 获取文章的标签列表
     *
     * @param postId 文章ID
     * @return 标签列表
     */
    @Override
    public ResponseResult<List<BlogTagVo>> getPostTags(Long postId) {
        // 1. 查询文章的所有标签
        List<BlogTag> tags = baseMapper.selectTagsByPostId(postId);
        
        // 2. 转换为VO对象
        List<BlogTagVo> tagVos = tags.stream().map(tag -> {
            BlogTagVo tagVo = new BlogTagVo();
            tagVo.setId(tag.getId());
            tagVo.setName(tag.getName());
            return tagVo;
        }).collect(Collectors.toList());
        
        return new ResponseResult<>(200, "获取成功", tagVos);
    }
}




