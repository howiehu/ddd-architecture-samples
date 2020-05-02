package dev.huhao.samples.ddd.blogservice.adapters.outbound.persistence;

import org.flywaydb.test.FlywayTestExecutionListener;
import org.flywaydb.test.annotation.FlywayTest;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

// 使用了 http://mybatis.org/spring-boot-starter/mybatis-spring-boot-test-autoconfigure 便于对 MyBatis 的 Mapper 进行与数据库间的集成测试
@MybatisTest
//使用了 https://github.com/flyway/flyway-test-extensions 来实现测试时重置数据库
@FlywayTest
//由于在 application.properties 中配置了数据库信息，所以关闭自动配置测试数据库的功能
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
/* 使用 flyway-test-extensions 的配置要求，由于覆盖了默认的 @TestExecutionListeners 配置，
   所以需要显式加入 DependencyInjectionTestExecutionListener.class，
   为了MybatisTest的事务回滚机制生效，需要显式加入 TransactionalTestExecutionListener.class，
   默认配置请看 @TestExecutionListeners 的实现*/
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        FlywayTestExecutionListener.class
})
public abstract class MapperIntegrationTestBase { //抽象的测试基类用于减少每一个测试类编写时的重复代码，每一个测试类必须继承该基类
}
