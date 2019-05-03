package study.huhao.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:flyway.properties")
public class JerseyWithMyBatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(JerseyWithMyBatisApplication.class, args);
    }

}
