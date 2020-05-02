package dev.huhao.samples.ddd.blogservice.adapters.grpc;

import dev.huhao.samples.ddd.blogservice.adapters.inbound.grpc.draft.DraftGrpcService;
import dev.huhao.samples.ddd.blogservice.application.usecase.EditBlogUseCase;
import net.devh.boot.grpc.client.autoconfigure.GrpcClientAutoConfiguration;
import net.devh.boot.grpc.server.autoconfigure.GrpcServerAutoConfiguration;
import net.devh.boot.grpc.server.autoconfigure.GrpcServerFactoryAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ImportAutoConfiguration({
        GrpcServerAutoConfiguration.class, // Create required server beans
        GrpcServerFactoryAutoConfiguration.class, // Select server implementation
        GrpcClientAutoConfiguration.class}) // Support @GrpcClient annotation
public class GrpcServiceIntegrationTestConfiguration {

    @Bean
    EditBlogUseCase editBlogUseCase() {
        return new EditBlogUseCase();
    }

    @Bean
    DraftGrpcService draftGrpcService() {
        return new DraftGrpcService(editBlogUseCase());
    }
}
