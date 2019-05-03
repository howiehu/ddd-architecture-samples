package study.huhao.demo.adapters.restapi.providers;

import study.huhao.demo.domain.core.excpetions.EntityNotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Map;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.status;


@Provider
public class EntityNotFoundExceptionMapper implements ExceptionMapper<EntityNotFoundException> {
    @Override
    public Response toResponse(EntityNotFoundException ex) {
        var entity = Map.of("message", ex.getMessage());
        return status(NOT_FOUND).entity(entity).build();
    }
}
