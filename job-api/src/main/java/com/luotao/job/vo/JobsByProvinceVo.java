package com.luotao.job.vo;

import lombok.Data;

/**
 * @Classname JobsByProvinceVo
 * @Description 按省份统计岗位，从数据库中查询每个省份的岗位数量，并将结果映射到 Vo 对象中。
 * @Version 1.0.0
 * @Date 2025/3/26 19:15
 * @Author LuoTao
 */
@Data
public class JobsByProvinceVo {
    private String province;
    private Integer quantity;
}
