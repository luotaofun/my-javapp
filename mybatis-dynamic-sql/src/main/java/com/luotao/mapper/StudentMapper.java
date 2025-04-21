package com.luotao.mapper;

import com.luotao.domain.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudentMapper {
    List<Student> getStudentList(  @Param("studentIds")List<Integer>studentIds,@Param("studentId") Integer studentId,@Param("studentName") String studentName,@Param("tableName") String tableName);
}
