package com.luotao.job.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger配置类，用于配置Swagger生成 RESTful API 文档
 * 该配置类指定了哪些接口将被生成文档，以及文档的基本信息
 */
@Configuration
public class SwaggerConfig {
    /**
     * 创建 RESTful API 文档的配置
     * 使用 Docket 对象来配置 Swagger，包括文档类型、API 信息选择器等
     *
     * @return Docket 实例，用于构建和配置 Swagger 文档
     */
    @Bean
    public Docket createRestApi(){
        // 创建并配置Docket对象，使用OpenAPI 3.0规范
        return new Docket(DocumentationType.OAS_30)
                // 设置API文档的基本信息，如标题、描述、版本等
                .apiInfo(apiInfo())
                .select()
                // 选择带有@RestController注解的类中的API进行文档化
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                // 对所有路径的API进行文档化
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 构建 API 文档的详细信息
     * 包括标题、描述、许可证信息、版本号等
     *
     * @return ApiInfo 实例，含了API文档的元数据信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Taoの博客系统")  // 设置API文档的标题
                .description("后端接口文档")     // 设置API文档的描述
                .license("luotao")               // 设置API文档的许可证信息
                .licenseUrl("https://luotaofun.github.io/") // 设置许可证的URL
                .version("1.0")                  // 设置API的版本
                .build();                        // 构建并返回ApiInfo对象
    }
}
