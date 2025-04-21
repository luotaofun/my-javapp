import com.luotao.domian.Employ;
import com.luotao.mapper.EmpMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname EmpTest
 * @Description TODO
 * @Version 1.0.0
 * @Date 2025/4/11 1:19
 * @Author LuoTao
 */
@Slf4j
public class EmpTest {
    private EmpMapper empMapper;
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
         empMapper = sqlSession.getMapper(EmpMapper.class);
    }

    @Test
    public void listTest()  {
        // 执行EmpMapper.xml中id="list"的SQL语句,将查询结果自动映射为Employ对象列表
        empMapper.list("t",200D).forEach(System.out::println);

        // 可以不用mapper,直接引用配置文件的命名空间下的id为list的SQL 查询。
        // 第一个参数是 SQL 语句的完整路径，格式为 命名空间 + SQL 语句的 id，例如 com.luotao.mapper.EmpMapper.list。
        //第二个参数是传递给 SQL 语句的参数，可以是一个 Map 或实体对象，
        // 这里通过 HashMap 传递 empName 和 minEmpSalary 参数，key是String类型的sql占位符，value是Object类型可以自动转换empName的String或minEmpSalary的Double类型。
//        sqlSession.selectList("com.luotao.mapper.EmpMapper.list",new HashMap<String,Object>(){{
//            put("empName", "t");           // 对应 SQL 中的 #{empName}
//            put("minEmpSalary", 200D);  // 对应 SQL 中的 #{minEmpSalary}
//        }}).forEach(System.out::println);
    }


    @Test
    public void listByResultMapTest(){
        empMapper.listByResultMap().forEach(System.out::println);
    }

    @Test
    public void getEmpByNameTest()  {
        // 执行EmpMapper.xml中id="getEmpByName"的SQL语句,将查询结果自动映射为Employ对象
        System.out.println(empMapper.getEmpByName("luotao","t_emp"));
    }

    @Test
    public void getEmpSalaryById()  {
        System.out.println(String.format("该员工薪资为%s",  empMapper.getEmpSalaryById(Integer.valueOf(1))));
    }

    @Test
    public void getMinMaxAvgSalary()  {
         empMapper.getMinMaxAvgSalary().entrySet().forEach(kv ->{
            System.out.println(String.format("%s=>%s",kv.getKey(),kv.getValue() ));
        });
    }
    @Test
    public void addEmployTest()  {
        Employ employ = new Employ();
        employ.setEmpName("旺财");
        employ.setEmpSalary(8000D);
        try {
            // 执行EmpMapper.xml中id="addEmploy"的SQL语句,将查询结果自动映射为Employ对象
            System.out.println(empMapper.addEmploy(employ));
            System.out.println(employ); // 如果配置了keyProperty="empId"，则会将生成的主键映射到实体类的empId属性
            sqlSession.commit();
        } catch (Exception e) {
            sqlSession.rollback();
            System.out.println(e.getMessage());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void addEmployByMap()  {
/*        Map map = Map.of(
                "empName","旺财",
                "empSalary",8000D);*/
        // key必须和实体的属性名对应才能映射结果
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("empName", "旺财1");
        map.put("empSalary", 8000D);
        try {
            // 执行EmpMapper.xml中id="addEmployByMap"的SQL语句,将查询结果自动映射为Map
            System.out.println(empMapper.addEmployByMap(map));
            sqlSession.commit();
        } catch (Exception e) {
            sqlSession.rollback();
            System.out.println(e.getMessage());
        } finally {
            sqlSession.close();
        }
    }

}
