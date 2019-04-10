package study.huhao.demo.common.configurations;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study.huhao.demo.common.converters.BlogIdConverter;

@Configuration
public class OrikaConfiguration {
    @Bean
    public MapperFacade mapperFactory() {
        var mapperFactory = new DefaultMapperFactory.Builder().build();

        var converterFactory = mapperFactory.getConverterFactory();
        converterFactory.registerConverter(new BlogIdConverter());

        return mapperFactory.getMapperFacade();
    }
}
