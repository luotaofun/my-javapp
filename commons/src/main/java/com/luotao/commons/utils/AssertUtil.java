package com.luotao.commons.utils;

import com.luotao.commons.constant.ApiConstant;
import com.luotao.commons.exception.ParameterException;
import org.apache.commons.lang3.StringUtils;


/**
 * 断言工具类
 *
 * @author LuoTao
 * @version 1.0.0
 * 2025/6/1 17:02
 */
public class AssertUtil {
    /**
    * 字符串非空
    *
    * @author: LuoTao
    * 2025-06-01 17:04:32
    **/
    public static void isNotEmpty(String str,String... msg){
        if(StringUtils.isBlank(str)){
            execute(msg);
        }
    }
    /**
    * 对象非空
    *
    * @author: LuoTao
    * 2025-06-01 17:06:37
    **/
    public static void isNotNull(Object obj,String... msg){
        if(obj == null){
            execute(msg);
        }
    }
    /**
    * 结果是否为真
    *
    * @author: LuoTao
    * 2025-06-01 17:07:17
    **/
    public static void isTrue(boolean isTrue,String... msg){
        if(!isTrue){
            execute(msg);
        }
    }
    /**
    *  抛出异常
    *
    * @author: LuoTao
    * 2025-06-01 17:08:05
    **/
    public static void execute(String... msg){
        String errorMsg = ApiConstant.ERROR_MSG;
        if(msg != null && msg.length > 0){
            errorMsg = msg[0];
        }
        throw new ParameterException(errorMsg);
    }
}