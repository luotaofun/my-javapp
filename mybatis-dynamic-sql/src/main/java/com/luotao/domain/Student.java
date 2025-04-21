package com.luotao.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname Student
 * @Description TODO
 * @Version 1.0.0
 * @Date 2025/4/16 5:30
 * @Author LuoTao
 */
@Data
// 开启二级缓存后实体类需要实现serializable接口,因为二级缓存会将对象序列化存储
public class Student implements Serializable {
    private Integer sId;
    private String sName;
}
