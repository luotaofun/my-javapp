//package com.luotao;
//
//
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
//import com.github.pagehelper.PageInterceptor;
//import com.luotao.domain.Student;
//import com.luotao.mapper.StudentMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.ibatis.io.Resources;
//import org.apache.ibatis.session.SqlSession;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.apache.ibatis.session.SqlSessionFactoryBuilder;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//
///**
// * @Classname StudentTest
// * @Description TODO
// * @Version 1.0.0
// * @Date 2025/4/11 1:19
// * @Author LuoTao
// */
//@Slf4j
//public class StudentTest {
//    private StudentMapper studentMapper;
//    private SqlSessionFactory sqlSessionFactory;
//    private SqlSession sqlSession; //SqlSession 的事务默认是被暂存的，直到显示调用commit方法才会提交到数据库
//    @BeforeEach // @BeforeEach 注解用于标记一个方法，在每个测试方法执行之前都会调用该方法。这通常用于初始化测试环境或资源。
//    public void setup() throws IOException {
//        // 步骤1：从类路径（src/main/resources）加载MyBatis全局配置文件
//        InputStream resourceAsStream = Resources.getResourceAsStream("mybatis-config.xml");
//        // 步骤2：根据配置文件构建数据库会话工厂
//         sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
//        // 步骤3：获取一个数据库连接会话
//         sqlSession = sqlSessionFactory.openSession();
//        // 步骤4：获取Mapper代理对象(自动生成EmpMapper接口的实现类)：父接口指向一个实现了EmpMapper的类，实现的细节在`EmpMapper.xml`中(通过EmpMapper.xml中的SQL配置绑定接口方法)
//        studentMapper = sqlSession.getMapper(StudentMapper.class);
//    }
//
//    @Test
//    public void getStudentListTest(){
///*        //select sid,sname from t_stu where sname like concat('%',?,'%')
//        studentMapper.getStudentList( null,null,"luotao","t_stu").forEach(System.out::println);
//        //select sid,sname from t_stu
//        studentMapper.getStudentList(null, null, null, "t_stu").forEach(System.out::println);
//        //select sid,sname from t_stu where sid=? or sname like concat('%',?,'%')
//        studentMapper.getStudentList(null, 1, "luotao", "t_stu").forEach(System.out::println);*/
//        PageHelper.startPage(1, 2);//启动分页，查询结果只返回第 1 页的 2 条记录。
//        //select sid,sname from t_stu where sid in ( ? , ? , ? )
//        List<Student> studentList = studentMapper.getStudentList(Arrays.asList(1, 2, 3), null, null, "t_stu");
//        PageInfo<Student> studentPageInfo = new PageInfo<>(studentList);//  PageInfo 是 PageHelper 提供的分页信息类，包含分页相关的详细信息，如总页数、总记录数、当前页码等
//        System.out.println(studentPageInfo.getList());
//    }
//
//    @Test
//    public void testSecondLevelCache() {
//        Map<String, Object> params = new HashMap<>();
//        params.put("studentIds", Arrays.asList(1, 2, 3));
//        params.put("studentId", null);
//        params.put("studentName", null);
//        params.put("tableName", "t_stu");
//
//        // 第一次查询，会从数据库中获取数据，并存入二级缓存
//        List<Student> studentList1 = sqlSession.selectList("com.luotao.mapper.StudentMapper.getStudentList",params);
//        System.out.println("第一次查询结果：" + studentList1);
//        // 提交事务，确保数据写入二级缓存
//        sqlSession.commit();
//        // 关闭当前会话
//        sqlSession.close(); //二级缓存只有在 SqlSession 提交或关闭后才会生效
//
//        // 重新打开一个新的会话（基于命名空间，跨sqlsession）
//        sqlSession = sqlSessionFactory.openSession();
//        studentMapper = sqlSession.getMapper(StudentMapper.class);
//        // 第二次查询，会从二级缓存中获取数据，而不是访问数据库
//        List<Student> studentList2 = sqlSession.selectList("com.luotao.mapper.StudentMapper.getStudentList",params);
//        System.out.println("第二次查询结果：" + studentList2);
//
//        // 验证两次查询结果是否相同
//        assert studentList1.equals(studentList2);
//    }
//
//
//}
