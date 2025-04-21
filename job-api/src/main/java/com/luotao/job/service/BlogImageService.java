package com.luotao.job.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.luotao.job.domain.BlogImage;
import com.luotao.job.utils.ResponseResult;

/**
 * 博客图片服务接口
 */
public interface BlogImageService extends IService<BlogImage> {
    /**
     * 分页查询图片列表
     *
     * @param postTitle 博客文章标题
     * @param page      页码
     * @param size      每页大小
     * @return 图片列表
     */
    IPage<BlogImage> listImages(String postTitle, Integer page, Integer size);

    /**
     * 删除图片
     *
     * @param imageId 图片ID
     * @return 删除结果
     */
    ResponseResult deleteImage(Long imageId);

    /**
     * 重命名图片
     *
     * @param imageId   图片ID
     * @param newName 新文件名
     * @return 重命名结果
     */
    ResponseResult renameImage(Long imageId, String newName);
}
