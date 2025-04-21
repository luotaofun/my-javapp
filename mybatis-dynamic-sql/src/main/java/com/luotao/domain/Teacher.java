package com.luotao.domain;

import lombok.Data;

import java.util.List;

/**
 * @Classname Teacher
 * @Description TODO
 * @Version 1.0.0
 * @Date 2025/4/16 5:30
 * @Author LuoTao
 */

@Data
public class Teacher {
    private Integer tId;
    private String tName;
    private List<Student> studentList; // 一对多关联，一个老师有多个学生
    private TeacherProfile teacherProfile; // 一对一关联，每个教师的详细信息
}
