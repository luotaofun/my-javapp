package com.luotao.commons.model.base;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体对象的基础公共属性
 *
 * @author LuoTao
 * @version 1.0.0
 * 2025/6/2 15:37
 */
@Getter
@Setter
public class BaseModel implements Serializable {
    private Integer id;
    /**
    * 创建时间
    *
    * @author: LuoTao
    * 2025-06-02 15:39:42
    **/
    private Date createDate;
    /**
     * 更新时间
     *
     * @author: LuoTao
     * 2025-06-02 15:39:42
     **/
    private Date updateDate;
    /**
     * 是否有效
     *
     * @author: LuoTao
     * 2025-06-02 15:39:42
     **/
    private int isValid;
}