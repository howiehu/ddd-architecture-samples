package study.huhao.demo.application;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:service.properties")
public class ApplicationConfiguration {
}
