package com.luotao.mapper;

import com.luotao.domian.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudentMapper {
    List<Student> getStudentListByTeacherId(@Param("teacherId") Integer teacherId);
}
