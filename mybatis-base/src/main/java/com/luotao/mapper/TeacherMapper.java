package com.luotao.mapper;

import com.luotao.domian.Teacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Classname TeacherMapper
 * @Description TODO
 * @Version 1.0.0
 * @Date 2025/4/16 5:35
 * @Author LuoTao
 */
public interface TeacherMapper {
    /**
    * @Description: 演示一对一(association javaType)和一对多（collection ofType）
    * @Author: LuoTao
    * @Date: 2025-04-16 17:53:50
    **/
    List<Teacher> getTeacherList();

    /**
     * @Description: 演示一对一(起别名的方式)
     * @Author: LuoTao
     * @Date: 2025-04-16 17:53:50
     **/
    List<Teacher> getTeacherListByAs();

    /**
    * @Description: 演示分步查询：1.通过tid查某位老师。2.通过查询结果的tid查这位老师名下的学生(引用StudentMapper中的getStudentListByTeacherId)
    * @Author: LuoTao
    * @Date: 2025-04-16 18:26:36
    **/
    Teacher getTeacherByIdStep(@Param("tid") Integer tid);
}
