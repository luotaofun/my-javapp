package com.luotao.job.utils;
import com.luotao.job.utils.enums.ResultCodeEnum;
import lombok.Data;


/**
 * @Classname ResponseResult
 * @Description 封装响应结果
 * @Version 1.css.0.0
 * @Date 2024/12/26 16:43
 * @Author LuoTao
 */
@Data
public class ResponseResult<T>{
    private Integer code;
    private String msg;
    private Object data;
    /**
     * @Description 自定义返回
     * @Author LuoTao
     * @Date 2024/12/26 16:52
     * @param code 自定义状态码
     * @param msg 自定义提示信息
     * @param data 自定义返回数据
     **/
    public ResponseResult(Integer code,String msg,T data){
        this.code=code;
        this.msg=msg;
        this.data=data;
    }

    /**
     * @Description 自定义返回-不返回对象
     **/
    public ResponseResult(Integer code,String msg){
        this.code=code;
        this.msg=msg;
        this.data=null;
    }

    /**
    * @Description: 请求成功-不返回对象，使用默认的成功消息
    * @Author: LuoTao
    * @Date: 2024-12-26 16:58:39
    **/
    public ResponseResult(){
        this.code = ResultCodeEnum.SUCCESS.getCode();
        this.msg=ResultCodeEnum.SUCCESS.getMsg();
        this.data=null;
    }

    /**
     * @Description 请求成功返回对象，使用默认的成功消息
     * @Author LuoTao
     * @Date 2024/12/26 17:11
     **/
    public ResponseResult(Object data) {
            this.code=ResultCodeEnum.SUCCESS.getCode();
            this.msg=ResultCodeEnum.SUCCESS.getMsg();
            this.data = data;
    }
}
