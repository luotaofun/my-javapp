package com.luotao.commons.model.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 公共返回对象
 *
 * @author LuoTao
 * @version 1.0.0
 * 2025/6/1 17:14
 */
@Getter
@Setter
@ApiModel(description = "返回说明")
public class ResultInfo<T> implements Serializable {
    @ApiModelProperty(value = "成功：1 失败：0")
    private Integer code;
    @ApiModelProperty(value = "描述信息")
    private String msg;
    @ApiModelProperty(value = "请求路径")
    private Object path;
    @ApiModelProperty(value = "返回数据对象")
    private T data;

}