package study.huhao.demo.configurations.orika.converters;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;
import study.huhao.demo.domain.models.user.UserId;

public class UserIdConverter extends BidirectionalConverter<UserId, String> {
    @Override
    public String convertTo(UserId source, Type<String> destinationType, MappingContext mappingContext) {
        return source == null ? null : source.toString();
    }

    @Override
    public UserId convertFrom(String source, Type<UserId> destinationType, MappingContext mappingContext) {
        return source == null ? null : UserId.valueOf(source);
    }
}
