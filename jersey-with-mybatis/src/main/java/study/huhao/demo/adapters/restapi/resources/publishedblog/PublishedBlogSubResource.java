package study.huhao.demo.adapters.restapi.resources.publishedblog;

import study.huhao.demo.application.QueryBlogUseCase;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import java.util.UUID;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Produces(APPLICATION_JSON)
public class PublishedBlogSubResource {
    private UUID id;
    private QueryBlogUseCase queryBlogUseCase;

    PublishedBlogSubResource(UUID id, QueryBlogUseCase queryBlogUseCase) {
        this.id = id;
        this.queryBlogUseCase = queryBlogUseCase;
    }

    @GET
    public PublishedBlogDto get() {
        return PublishedBlogDto.of(queryBlogUseCase.get(id));
    }
}
