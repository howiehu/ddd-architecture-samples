package study.huhao.demo.common.converters;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;
import study.huhao.demo.domain.models.blog.BlogId;

public class BlogIdConverter extends BidirectionalConverter<BlogId, String> {

    @Override
    public String convertTo(BlogId source, Type<String> destinationType, MappingContext mappingContext) {
        return source.toString();
    }

    @Override
    public BlogId convertFrom(String source, Type<BlogId> destinationType, MappingContext mappingContext) {
        return BlogId.of(source);
    }
}
