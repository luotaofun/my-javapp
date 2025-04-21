package com.luotao.job.utils.enums;

import lombok.Getter;

/**
 * @Classname ResultCodeEnum
 * @Description 枚举类：统一响应状态码
 * @Version 1.css.0.0
 * @Date 2024/12/24 16:56
 * @Author LuoTao
 */
@Getter // 自动生成getter方法
public enum ResultCodeEnum {
    SUCCESS(200, "请求成功"),
    PARAM_ERROR(400, "参数错误"),
    NOT_FOUND(404, "接口不存在"),
    SERVER_ERROR(500, "服务器错误"),
    DB_ERROR(505, "数据库错误"),
    UNKNOWN_ERROR(600, "未知错误"),
    UNIQUE_KEY_ERROR(506, "唯一键冲突")
    ;
    /**
     响应状态码
     **/
    private Integer code;
    /**
     状态码描述
     **/
    private String msg;

    ResultCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
