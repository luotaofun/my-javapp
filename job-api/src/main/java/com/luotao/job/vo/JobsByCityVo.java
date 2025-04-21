package com.luotao.job.vo;

import lombok.Data;

/**
 * @Classname JobsByCityVo
 * @Description 按城市统计岗位，从数据库中查询每个城市的岗位数量，并将结果映射到 Vo 对象中。
 * @Version 1.0.0
 * @Date 2025/3/26 19:15
 * @Author LuoTao
 */
@Data
public class JobsByCityVo {
    private String city;
    private Integer quantity;
}
