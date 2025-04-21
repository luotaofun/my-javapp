package com.luotao.job.vo;

import lombok.Data;

/**
 * @Classname JobsByCategoryVo
 * @Description 从数据库中查询每个类别的岗位数量，并将结果映射到 JobsByCategoryVo 对象中。
 * 封装岗位类别及其数量的信息，便于在不同层之间传递数据或作为接口返回结果。它是一个典型的值对象，专注于数据传输和展示，而不包含复杂的业务逻辑。
 * @Version 1.0.0
 * @Date 2025/3/26 18:44
 * @Author LuoTao
 */
@Data // 自动getsetter
public class JobsByCategoryVo {
    private String categoryName;
    private String categoryDesc;
    private Integer quantity;//岗位数量
}
