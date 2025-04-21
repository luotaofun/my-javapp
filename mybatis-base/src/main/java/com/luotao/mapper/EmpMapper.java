package com.luotao.mapper;

import com.luotao.domian.Employ;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EmpMapper {
    // 查询所有员工
    List<Employ> list(@Param("empName")String empName,@Param("minEmpSalary") Double minEmpSalary);
    List<Employ> listByResultMap();

    //  @Param 注解为方法参数指定一个名称，可以在 XML 中通过该名称引用这些参数，MyBatis 会将所有参数封装为一个 Map，键为 @Param 指定的名称，值为参数的实际值。
    Employ getEmpByName(@Param("abc")String empName,@Param("tableName") String tableName);

    Integer addEmploy(Employ employ);

    Integer addEmployByMap(Map map);

    Double getEmpSalaryById(Integer id);

    //@MapKey("emp_id") //@MapKey的作用是将查询结果映射为一个嵌套的 Map，外层 Map 的键是 @MapKey 指定的列值，内层 Map 是其他列名和列值的键值对。如果未指定 @MapKey：MyBatis 将查询结果映射为一个普通的 Map，键是列名，值是对应的列值。
    Map<String,Double> getMinMaxAvgSalary();

}
