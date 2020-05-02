package dev.huhao.samples.ddd.blogservice.adapters.grpc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringBootTest
@SpringJUnitConfig(classes = {GrpcServiceIntegrationTestConfiguration.class})
// Ensures that the grpc-server is properly shutdown after each test
// Avoids "port already in use" during tests
@DirtiesContext
public class GrpcServiceIntegrationTestBase {

}
