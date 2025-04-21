package com.luotao;

import com.luotao.domain.Teacher;
import com.luotao.mapper.TeacherMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Classname EmpTest
 * @Description TODO
 * @Version 1.0.0
 * @Date 2025/4/11 1:19
 * @Author LuoTao
 */
@Slf4j
public class TeacherTest {
    private TeacherMapper teacherMapper;
    private SqlSession sqlSession; //SqlSession 的事务默认是被暂存的，直到显示调用commit方法才会提交到数据库
    @BeforeEach // @BeforeEach 注解用于标记一个方法，在每个测试方法执行之前都会调用该方法。这通常用于初始化测试环境或资源。
    public void setup() throws IOException {
        // 步骤1：从类路径（src/main/resources）加载MyBatis全局配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("mybatis-config.xml");
        // 步骤2：根据配置文件构建数据库会话工厂
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        // 步骤3：获取一个数据库连接会话
         sqlSession = sqlSessionFactory.openSession();
        // 步骤4：获取Mapper代理对象(自动生成EmpMapper接口的实现类)：父接口指向一个实现了EmpMapper的类，实现的细节在`EmpMapper.xml`中(通过EmpMapper.xml中的SQL配置绑定接口方法)
        teacherMapper = sqlSession.getMapper(TeacherMapper.class);
    }


}
