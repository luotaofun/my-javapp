package com.luotao.job.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.List;

/**
 * @author luotao
 * @description 文件上传配置类
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "file.upload")
public class FileUploadConfig {
    /**
     * 文件上传根路径
     */
    private String path;

    /**
     * 允许上传的文件类型
     */
    private List<String> allowedTypes; //Spring Boot 会自动将连字符分隔的形式转换为驼峰命名法进行匹配，allowed-types。

    // 在类中手动定义了 getPath 方法，则会覆盖 @Data 自动生成的 getter 方法
    public String getPath() {
        // 确保返回的路径在不同操作系统上都能正确使用
        return path.replace("/", File.separator) ;
    }
}