package com.luotao.plugin;


import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.stereotype.Component;

import java.sql.Statement;
import java.util.Properties;

/**
 * @Classname SqlLogPlugin
 * @Description 记录sql查询耗时，插件开发需要去MyBatis的配置文件中注册插件
 * @Version 1.0.0
 * @Date 2025/4/11 2:30
 * @Author LuoTao
 */
@Slf4j
@Component
@Intercepts({
        @Signature(
                type = StatementHandler.class,
                method = "query",
                args={Statement.class, ResultHandler.class}
        )
})
public class SqlLogPlugin implements Interceptor {
    // public static final Logger log = LoggerFactory.getLogger(SqlLogPlugin.class);
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        long b = System.currentTimeMillis();
        String sql = ((StatementHandler) invocation.getTarget()).getBoundSql().getSql().replaceAll("\\s+", " ").trim();
        Object obj = invocation.proceed();
        log.info("{} cost {}ms", sql, System.currentTimeMillis() - b);
        return obj;
    }

}
