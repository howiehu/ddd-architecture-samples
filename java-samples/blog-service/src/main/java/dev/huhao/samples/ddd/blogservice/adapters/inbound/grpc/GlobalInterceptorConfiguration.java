package dev.huhao.samples.ddd.blogservice.adapters.inbound.grpc;

import dev.huhao.samples.ddd.blogservice.adapters.inbound.grpc.interceptors.GlobalExceptionHandlerInterceptor;
import net.devh.boot.grpc.server.interceptor.GlobalServerInterceptorConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalInterceptorConfiguration {

    @Bean
    public GlobalServerInterceptorConfigurer globalInterceptorConfigurerAdapter() {
        return registry -> registry.addServerInterceptors(new GlobalExceptionHandlerInterceptor());
    }

}
