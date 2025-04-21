package com.luotao.job.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luotao.job.domain.BlogImage;
import com.luotao.job.mapper.BlogImageMapper;
import com.luotao.job.service.BlogImageService;
import com.luotao.job.utils.ResponseResult;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;


/**
* @author T
* @description 针对表【blog_image(博客图片表)】的数据库操作Service实现
* @createDate 2025-04-01 00:28:14
*/
@Service
public class BlogImageServiceImpl extends ServiceImpl<BlogImageMapper, BlogImage>
    implements BlogImageService{
    @Resource
    private BlogImageMapper blogImageMapper;
    @Override
    public IPage<BlogImage> listImages(String postTitle, Integer page, Integer size) {
        // 构建查询条件
        LambdaQueryWrapper<BlogImage> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(postTitle)) {
            queryWrapper.eq(BlogImage::getPostTitle, postTitle);
        }
        queryWrapper.orderByDesc(BlogImage::getUploadTime); //引用 BlogImage 类的 uploadTime 字段。

        // 分页查询
        Page<BlogImage> pageResult = new Page<>(page, size);
//        page(pageResult, queryWrapper);

        // 返回结果
//        List<BlogImage> images = pageResult.getRecords();
        Page<BlogImage> imagesPage = blogImageMapper.selectPage(pageResult,queryWrapper);
        return imagesPage;
//        return new ResponseResult(200,"获取成功",images);

    }

    @Override
    public ResponseResult deleteImage(Long imageId) {

        // 查询图片信息
        BlogImage image = getById(imageId);
        if (image == null) {
            return new ResponseResult(404,"图片不存在");
        }

        // 删除图片文件
        File file = new File(image.getPath());
        if (file.exists() && !file.delete()) {
            return new ResponseResult(500,"删除图片文件失败");
        }

        // 删除图片记录
        removeById(imageId);

        return new ResponseResult<>(200, "删除成功");
    }

    @Override
    public ResponseResult renameImage(Long imageId, String newName) {
        // 查询图片信息
        BlogImage image = getById(imageId);
        if (image == null) {
            return new ResponseResult(404,"图片不存在");
        }

        // 重命名图片文件
        File oldFile = new File(image.getPath());
        String newPath = oldFile.getParent() + File.separator + newName;
        File newFile = new File(newPath);
        if (!oldFile.renameTo(newFile)) {
            return  new ResponseResult(500,"重命名图片文件失败");
        }

        // 更新图片记录
        image.setFilename(newName);
        image.setPath(newPath);
        updateById(image);

        return new ResponseResult<>(200, "重命名成功");
    }
}




