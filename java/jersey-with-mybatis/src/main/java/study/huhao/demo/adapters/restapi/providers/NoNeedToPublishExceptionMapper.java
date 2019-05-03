package study.huhao.demo.adapters.restapi.providers;

import study.huhao.demo.domain.models.blog.exceptions.NoNeedToPublishException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Map;

import static javax.ws.rs.core.Response.Status.CONFLICT;
import static javax.ws.rs.core.Response.status;


@Provider
public class NoNeedToPublishExceptionMapper implements ExceptionMapper<NoNeedToPublishException> {
    @Override
    public Response toResponse(NoNeedToPublishException ex) {
        var entity = Map.of("message", ex.getMessage());
        return status(CONFLICT).entity(entity).build();
    }
}
