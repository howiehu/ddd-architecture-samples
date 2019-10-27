package study.huhao.demo.adapters.inbound.rest.resources;

import io.restassured.RestAssured;
import org.flywaydb.test.FlywayTestExecutionListener;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@ExtendWith(SpringExtension.class)
// 由于完整启动了 Servlet 进行从 HTTP 请求开始的集成测试，所以需要在测试时随机分配端口以便于测试
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
/* 使用 flyway-test-extensions 的配置要求，由于覆盖了默认的 @TestExecutionListeners 配置，
   所以需要显式加入 DependencyInjectionTestExecutionListener.class，默认配置请看 @TestExecutionListeners 的实现*/
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class})
public abstract class ResourceTest {

    // 注入分配的随机端口供测试代码使用
    @LocalServerPort
    private int port;

    @BeforeEach
    //使用了 https://github.com/flyway/flyway-test-extensions 来实现测试时重置数据库
    @FlywayTest
    void init() {
        // 配置 RestAssured 使用随机端口
        RestAssured.port = port;
    }
}
