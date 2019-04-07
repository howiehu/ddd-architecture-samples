package study.huhao.demo.adapters.persistence;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@EnableAutoConfiguration
@ComponentScan("study.huhao.demo.adapters.persistence")
@PropertySource("classpath:persistence.properties")
public class PersistenceConfiguration {

}
