package com.luotao.domian;

import lombok.Data;

/**
 * @Classname Employ
 * @Description 员工实体类
 * @Version 1.0.0
 * @Date 2025/4/11 0:48
 * @Author LuoTao
 */
@Data
public class Employ {
    private Integer empId;
    private String empName;
    private Double empSalary;
    private Double doubleEmpSalary; //非数据库字段
}
