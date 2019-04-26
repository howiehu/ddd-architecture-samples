package study.huhao.demo.configurations.orika;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study.huhao.demo.configurations.orika.converters.BlogIdConverter;
import study.huhao.demo.configurations.orika.converters.UserIdConverter;

@Configuration
public class OrikaConfiguration {

    @Bean
    public MapperFacade mapperFacade() {
        var mapperFactory = new DefaultMapperFactory.Builder().build();

        var converterFactory = mapperFactory.getConverterFactory();
        converterFactory.registerConverter(new BlogIdConverter());
        converterFactory.registerConverter(new UserIdConverter());

        return mapperFactory.getMapperFacade();
    }
}
