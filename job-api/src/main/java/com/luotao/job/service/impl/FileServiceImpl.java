package com.luotao.job.service.impl;

import com.luotao.job.config.FileUploadConfig;
import com.luotao.job.domain.BlogImage;
import com.luotao.job.service.FileService;
import com.luotao.job.service.BlogImageService;
import com.luotao.job.utils.ResponseResult;
import com.luotao.job.utils.enums.FileConflictStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author luotao
 * @description 文件服务实现类
 */
@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileUploadConfig fileUploadConfig;

    @Autowired
    private BlogImageService blogImageService;

    @Override
    public ResponseResult uploadMarkdown(MultipartFile file, FileConflictStrategy strategy) {
        // 1. 校验文件是否为空
        if (file.isEmpty()) {
            return new ResponseResult(400, "文件不能为空");
        }

        // 2. 校验文件类型
        String originalFilename = file.getOriginalFilename();
        String fileExtension = StringUtils.getFilenameExtension(originalFilename);
        if (!isAllowedFileType(fileExtension)) {
            return new ResponseResult(400, "不支持的文件类型");
        }

        try {
            // 3. 生成文件保存路径
            String savePath = generateSavePath(originalFilename);
            Path path = Paths.get(savePath);

            // 4. 处理文件冲突
            if (Files.exists(path)) {
                switch (strategy) {
                    case REJECT:
                        return new ResponseResult(400, "文件已存在");
                    case RENAME:
                        path = generateUniqueFilePath(path);
                        break;
                    case OVERWRITE:
                        // 默认覆盖，无需特殊处理
                        break;
                }
            }

            // 5. 确保目录存在
            Files.createDirectories(path.getParent());

            // 6. 保存文件
            file.transferTo(path);

            // 7. 返回文件访问路径
            String accessPath = path.toString().replace(fileUploadConfig.getPath(), "");
            return new ResponseResult(200, "上传成功", accessPath);
        } catch (IOException e) {
            log.error("文件上传失败：", e);
            return new ResponseResult(500, "文件上传失败：" + e.getMessage());
        }
    }

    @Override
    public ResponseResult readMarkdownContent(String filePath) {
        try {
            // 1. 构建完整的文件路径
//            Path fullPath = Paths.get(fileUploadConfig.getPath(), filePath);
            Path fullPath = Paths.get(filePath);

            // 2. 检查文件是否存在
            if (!Files.exists(fullPath)) {
                return new ResponseResult(404, "文件不存在");
            }
            
            // 3. 检查文件类型
            String fileExtension = StringUtils.getFilenameExtension(fullPath.toString());
            if (!isAllowedFileType(fileExtension)) {
                return new ResponseResult(400, "不支持的文件类型");
            }
            
            // 4. 读取文件内容
            String content = new String(Files.readAllBytes(fullPath), StandardCharsets.UTF_8);
            
            // 5. 返回文件内容
            return new ResponseResult(200, "读取成功", content);
        } catch (IOException e) {
            log.error("读取文件失败：", e);
            return new ResponseResult(500, "读取文件失败：" + e.getMessage());
        }
    }

    @Override
    public ResponseResult uploadImages(MultipartFile[] files, String postTitle, FileConflictStrategy strategy) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, String>> successList = new ArrayList<>();
        List<Map<String, Object>> errorList = new ArrayList<>();
        // 根据单个文件上传的结果，将成功或失败的信息分类存储
        for (MultipartFile file : files) {
            try {
                ResponseResult singleResult = uploadSingleImage(file, postTitle, strategy);
                if (singleResult.getCode() == 200) {
                    successList.add((Map<String, String>) singleResult.getData()); // 强制转换为 Map<String, String> 类型
                } else {
                    /**
                     外层大括号 {}：表示创建一个匿名内部类，继承自 HashMap。
                     内层大括号 {}：表示匿名内部类的实例初始化块，在这里可以调用 put 方法向 HashMap 中添加键值对
                     Map<String, Object> map = new HashMap<>();
                     map.put("filename", file.getOriginalFilename());
                     map.put("error", singleResult.getMsg());
                     errorList.add(map);
                     **/
                    errorList.add(new HashMap<String, Object>(){{
                        put("filename", file.getOriginalFilename());
                        put("error", singleResult.getMsg());
                    }});
                }
            } catch (Exception e) {
                errorList.add(new HashMap<String, Object>(){{
                    put("filename", file.getOriginalFilename());
                    put("error", "处理过程中发生异常: " + e.getMessage());
                }});
            }
        }
    
        result.put("success", successList);
        result.put("error", errorList);
        return new ResponseResult(200, "批量上传完成", result);
    }

    private ResponseResult uploadSingleImage(MultipartFile file, String postTitle, FileConflictStrategy strategy) {
        // 1. 校验文件是否为空
        if (file.isEmpty()) {
            return new ResponseResult(400, "文件不能为空");
        }

        // 2. 校验文件类型
        String originalFilename = file.getOriginalFilename();
        String fileExtension = StringUtils.getFilenameExtension(originalFilename);
        if (!isAllowedImageType(fileExtension)) {
            return new ResponseResult(400, "不支持的图片类型");
        }

        try {
            // 3. 生成图片存储目录和文件绝对路径
            String storageDir = generateStorageDir(postTitle);
            Path filePath = Paths.get(storageDir, originalFilename);

            // 4. 处理图片冲突
            if (Files.exists(filePath)) {
                switch (strategy) {
                    case REJECT:
                        return new ResponseResult(400, "文件已存在");
                    case RENAME:
                        filePath = generateUniqueFilePath(filePath);
                        break;
                    case OVERWRITE:
                        break;
                }
            }

            // 5. 保存文件
            Files.createDirectories(filePath.getParent());
            file.transferTo(filePath);

            // 获取最终保存的文件名
            String newFilename = filePath.getFileName().toString();
            // 6. 生成访问URL和Markdown引用路径
            String accessUrl = generateAccessUrl(postTitle, newFilename);
            String markdownPath = generateMarkdownPath(postTitle, newFilename);

            // 7. 保存图片信息到数据库
            BlogImage blogImage = new BlogImage();
            blogImage.setFilename(newFilename);
            blogImage.setPath(accessUrl);
            blogImage.setPostTitle(postTitle);
            blogImage.setUrl(markdownPath);
            blogImage.setSize(file.getSize());
            blogImageService.save(blogImage);


            // 8. 返回结果
            Map<String, String> data = new HashMap<>();
            data.put("fileName",newFilename);
            data.put("url", accessUrl);
            data.put("markdownPath", markdownPath);
            return new ResponseResult(200, "上传成功", data);
        } catch (IOException e) {
            log.error("图片上传失败：", e);
            return new ResponseResult(500, "图片上传失败：" + e.getMessage());
        }
    }

    /**
     * 检查文件类型是否允许
     */
    private boolean isAllowedFileType(String fileExtension) {
        if (fileExtension == null) {
            return false;
        }
        return fileUploadConfig.getAllowedTypes().contains("." + fileExtension.toLowerCase());
    }

    /**
     * 检查图片类型是否允许
     */
    private boolean isAllowedImageType(String fileExtension) {
        if (fileExtension == null) {
            return false;
        }
//        return fileUploadConfig.getAllowedImageTypes().contains("." + fileExtension.toLowerCase());
        return fileUploadConfig.getAllowedTypes().contains("." + fileExtension.toLowerCase());
    }

    /**
     * 生成文件保存路径
     */
    private String generateSavePath(String originalFilename) {
        // 1. 生成日期目录
//        String dateDir = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        
        // 2. 组合完整路径
//        return fileUploadConfig.getPath() + dateDir + File.separator + originalFilename;
        return fileUploadConfig.getPath()  + File.separator + originalFilename;
    }

    /**
     * 根据原始文件路径生成一个唯一的文件路径
     * 如果文件已存在，则在文件名后添加递增的数字以确保路径唯一
     *
     * @param original 原始文件路径
     * @return 生成唯一的文件路径（通过添加序号）
     */
    private Path generateUniqueFilePath(Path original) {
        // 获取原始路径的父目录
        Path parent = original.getParent();
        // 获取原始文件名
        String filename = original.getFileName().toString();
        // 移除文件名中的扩展名
        String nameWithoutExt = StringUtils.stripFilenameExtension(filename);
        // 获取文件名的扩展名
        String extension = StringUtils.getFilenameExtension(filename);

        // 初始化计数器用于生成唯一的文件名
        int counter = 1;
        // 初始化新路径为原始路径
        Path newPath = original;

        // 循环直到找到一个不存在的文件名
        while (Files.exists(newPath)) {
            // 根据计数器生成新的文件名
            String newName = String.format("%s(%d).%s", nameWithoutExt, counter++, extension);
            // 在父目录下解析新的文件路径
            newPath = parent.resolve(newName);
        }

        // 返回唯一的文件路径
        return newPath;
    }

    /**
     * 生成图片存储目录
     */
    private String generateStorageDir(String postTitle) {
        // 实现生成图片存储目录的逻辑
        return fileUploadConfig.getPath()  + "images" + File.separator + postTitle;
    }



    /**
     * 生成访问URL
     */
    private String generateAccessUrl(String postTitle, String filename) {
        // 实现生成访问URL的逻辑
        return fileUploadConfig.getPath() + "images" + File.separator + postTitle + File.separator + filename;
    }

    /**
     * 生成Markdown引用路径
     */
    private String generateMarkdownPath(String postTitle, String filename) {
        // 实现生成Markdown引用路径的逻辑
        // 相对路径 "markdownPath": "./文件名/文件名.jpg"
        return "." + File.separator + postTitle + File.separator + filename;
    }

} 