package study.huhao.name.springwithjpa.adapters.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "study.huhao.name.springwithjpa")
public class SpringWithJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringWithJpaApplication.class, args);
    }

}
