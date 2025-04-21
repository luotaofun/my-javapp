package com.luotao.job.controller;

import com.luotao.job.service.FileService;
import com.luotao.job.utils.ResponseResult;
import com.luotao.job.utils.enums.FileConflictStrategy;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author luotao
 * @description 文件上传控制器
 */
@Slf4j
@RestController
@RequestMapping("/file")
@Api(tags = "文件上传接口")
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * 上传Markdown文件
     *
     * @param file 文件
     * @param strategy 冲突处理策略
     * @return 上传结果
     */
    @PostMapping("/markdown")
    @ApiOperation("上传Markdown文件")
    public ResponseResult uploadMarkdown(
            @RequestPart("file") MultipartFile file,
            @RequestParam(defaultValue = "RENAME") FileConflictStrategy strategy) {
        log.info("上传Markdown文件，文件名：{}，冲突处理策略：{}", file.getOriginalFilename(), strategy);
        return fileService.uploadMarkdown(file, strategy);
    }

    /**
     * 读取Markdown文件内容
     *
     * @param filePath 文件相对路径
     * @return 文件内容
     */
    @GetMapping("/markdown")
    @ApiOperation("读取Markdown文件内容")
    public ResponseResult readMarkdownContent(@RequestParam String filePath) {
        log.info("读取Markdown文件内容，文件路径：{}", filePath);
        return fileService.readMarkdownContent(filePath);
    }

    /**
     * 上传图片文件
     *
     * @param file 图片文件
     * @param postTitle 博客文章标题
     * @param strategy 冲突处理策略
     * @return 上传结果,包含访问URL和Markdown引用路径
     */
    @PostMapping("/image")
    @ApiOperation("上传图片文件")
    public ResponseResult uploadImage(
            @RequestPart("files") MultipartFile[] files,
            @RequestParam("postTitle") String postTitle,
            @RequestParam(defaultValue = "RENAME") FileConflictStrategy strategy) {
        log.info("上传图片文件,文章标题: {}, 文件名: {}, 冲突处理策略: {}", postTitle, files.length, strategy);
        return fileService.uploadImages(files, postTitle, strategy);
    }
} 