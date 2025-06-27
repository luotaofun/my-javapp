package com.luotao.commons.exception;

import com.luotao.commons.constant.ApiConstant;
import lombok.Getter;
import lombok.Setter;

/**
 * 全局异常类
 *
 * @author LuoTao
 * @version 1.0.0
 * 2025/6/1 16:56
 */
@Getter
@Setter
public class ParameterException extends RuntimeException{
    private Integer errorCode;
    public ParameterException(){
        super(ApiConstant.ERROR_MSG);
        this.errorCode = ApiConstant.ERROR_CODE;
    }
    public ParameterException(Integer errorCode){
        this.errorCode = errorCode;
    }
    public ParameterException(String msg){
        super(msg);
        this.errorCode = ApiConstant.ERROR_CODE;
    }
    public ParameterException(String msg,Integer errorCode){
        super(msg);
        this.errorCode = errorCode;
    }
}