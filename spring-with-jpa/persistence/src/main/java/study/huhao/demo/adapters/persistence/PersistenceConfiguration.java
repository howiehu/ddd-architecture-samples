package study.huhao.demo.adapters.persistence;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;


@Configuration
@EnableAutoConfiguration
@ComponentScan("study.huhao.demo.adapters.persistence")
@PropertySources({
        @PropertySource("classpath:persistence.properties"),
        @PropertySource("classpath:flyway.properties")
})
public class PersistenceConfiguration {

}
