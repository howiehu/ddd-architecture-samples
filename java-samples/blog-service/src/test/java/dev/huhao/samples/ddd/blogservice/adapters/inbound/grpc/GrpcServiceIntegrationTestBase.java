package dev.huhao.samples.ddd.blogservice.adapters.inbound.grpc;

import net.devh.boot.grpc.client.autoconfigure.GrpcClientAutoConfiguration;
import net.devh.boot.grpc.server.autoconfigure.GrpcServerAutoConfiguration;
import net.devh.boot.grpc.server.autoconfigure.GrpcServerFactoryAutoConfiguration;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
public class GrpcServiceIntegrationTestBase {

}
