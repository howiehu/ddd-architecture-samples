package study.huhao.demo.configurations.orika;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrikaConfiguration {

    @Bean
    public MapperFacade mapperFacade() {
        var mapperFactory = new DefaultMapperFactory.Builder().build();

        return mapperFactory.getMapperFacade();
    }
}
