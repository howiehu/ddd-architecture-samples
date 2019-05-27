package study.huhao.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@PropertySource("classpath:flyway.properties")
public class SpringWithJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringWithJpaApplication.class, args);
    }

}
