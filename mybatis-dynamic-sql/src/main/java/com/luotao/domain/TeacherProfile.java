package com.luotao.domain;

import lombok.Data;

/**
 * @Classname TeacherProfile
 * @Description 每个教师的详细信息，教师表和教师资料表一对一
 * @Version 1.0.0
 * @Date 2025/4/16 17:00
 * @Author LuoTao
 */
@Data
public class TeacherProfile {
    private Integer profileId;
    private Integer teacherId;
    private String profileInfo;
}
