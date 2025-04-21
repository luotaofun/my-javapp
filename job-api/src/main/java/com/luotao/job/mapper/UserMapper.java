package com.luotao.job.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luotao.job.domain.User;

/**
 * @author luotao
 * @description 用户Mapper接口
 */
public interface UserMapper extends BaseMapper<User> {
    /**
     * BaseMapper提供了基础的CRUD方法:
     * - insert: 插入一条记录
     * - deleteById: 根据ID删除
     * - updateById: 根据ID更新
     * - selectById: 根据ID查询
     * - selectList: 查询列表
     * - selectCount: 查询总记录数
     * 等等...
     */
}