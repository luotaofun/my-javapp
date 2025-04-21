package com.luotao.job.utils.exception;


import com.luotao.job.utils.ResponseResult;
import com.luotao.job.utils.enums.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Classname GlobalExceptionHandler
 * @Description 全局异常处理
 * @Version 1.css.0.0
 * @Date 2024/12/27 15:49
 * @Author LuoTao
 */
@Slf4j //注解创建日志对象
@RestControllerAdvice
public class GlobalExceptionHandler {

    // public static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);// 手动创建日志对象
    /**
     * @Description 参数校验异常处理
     **/
    @ExceptionHandler(BindException.class)
    public ResponseResult bindException(BindException e) {
        /**
         * @Description 从BindingResult对象中获取错误信息
         **/
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder stringBuilder = new StringBuilder("校验失败：");
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            stringBuilder
                    .append(fieldError.getField())
                    .append(":")
                    .append(fieldError.getDefaultMessage())
                    .append(",");
        }
        String msg = stringBuilder.toString();
        log.error(ResultCodeEnum.PARAM_ERROR.getCode() + msg);//记录日志：将错误代码和错误消息记录到日志中
        return new ResponseResult(ResultCodeEnum.PARAM_ERROR.getCode(),msg);
    }

    /**
     * @Description 唯一键异常处理
     **/
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseResult duplicateKeyException(Exception e) {
        log.error(ResultCodeEnum.UNIQUE_KEY_ERROR.getCode() + e.getCause().getMessage());
        return new ResponseResult(ResultCodeEnum.UNIQUE_KEY_ERROR.getCode(),e.getCause().getMessage());
    }

    /**
     * @Description 未知异常处理
     **/
    @ExceptionHandler(Exception.class)
    public ResponseResult unknownException(Exception e) {
        log.error(ResultCodeEnum.UNKNOWN_ERROR.getCode() + e.getMessage());
        return new ResponseResult(ResultCodeEnum.UNKNOWN_ERROR.getCode(),e.getMessage());
    }
}
