package dev.huhao.samples.ddd.blogservice.adapters.inbound.grpc;

import net.devh.boot.grpc.client.autoconfigure.GrpcClientAutoConfiguration;
import net.devh.boot.grpc.server.autoconfigure.GrpcServerAutoConfiguration;
import net.devh.boot.grpc.server.autoconfigure.GrpcServerFactoryAutoConfiguration;
import org.springframework.test.annotation.Rollback;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ImportAutoConfiguration({
        GrpcServerAutoConfiguration.class, // Create required server beans
        GrpcServerFactoryAutoConfiguration.class, // Select server implementation
        GrpcClientAutoConfiguration.class // Support @GrpcClient annotation
})
// Ensures that the grpc-server is properly shutdown after each test
// Avoids "port already in use" during tests
@DirtiesContext
//由于在 application.yml 中配置了数据库信息，所以关闭自动配置测试数据库的功能
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
/* 使用 flyway-test-extensions 的配置要求，由于覆盖了默认的 @TestExecutionListeners 配置，
   所以需要显式加入 DependencyInjectionTestExecutionListener.class，默认配置请看 @TestExecutionListeners 的实现*/
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
public abstract class GrpcServiceIntegrationTestBase {

    @BeforeEach
    void init() {
    }
}
