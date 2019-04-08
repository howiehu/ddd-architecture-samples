package study.huhao.demo.adapters.api.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import study.huhao.demo.domain.models.base.EntityId;

import java.io.IOException;

@JsonComponent
public class EntityIdSerializer extends JsonSerializer<EntityId> {
    @Override
    public void serialize(EntityId value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.toString());
    }
}
