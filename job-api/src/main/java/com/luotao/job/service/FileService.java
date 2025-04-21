package com.luotao.job.service;

import com.luotao.job.utils.ResponseResult;
import com.luotao.job.utils.enums.FileConflictStrategy;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author luotao
 * @description 文件服务接口
 */
public interface FileService {
    /**
     * 上传Markdown文件
     *
     * @param file 文件
     * @param strategy 文件冲突处理策略
     * @return 上传结果
     */
    ResponseResult uploadMarkdown(MultipartFile file, FileConflictStrategy strategy);
    
    /**
     * 读取Markdown文件内容
     *
     * @param filePath 文件相对路径
     * @return 文件内容
     */
    ResponseResult readMarkdownContent(String filePath);
    
    /**
     * 批量上传图片文件
     *
     * @param file 图片文件
     * @param postTitle 博客文章标题
     * @param strategy 文件冲突处理策略
     * @return 上传结果,包含访问URL和Markdown引用路径
     */
    ResponseResult uploadImages(MultipartFile[] files, String postTitle, FileConflictStrategy strategy);
} 