package dev.huhao.samples.ddd.blogservice.adapters.grpc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringBootTest(properties = {
        "grpc.server.inProcessName=test", // Enable inProcess server
        "grpc.server.port=-1", // Disable external server
        "grpc.client.inProcess.address=in-process:test" // Configure the client to connect to the inProcess server
})
@SpringJUnitConfig(classes = {GrpcServiceIntegrationTestConfiguration.class})
// Ensures that the grpc-server is properly shutdown after each test
// Avoids "port already in use" during tests
@DirtiesContext
public class GrpcServiceIntegrationTestBase {

}
