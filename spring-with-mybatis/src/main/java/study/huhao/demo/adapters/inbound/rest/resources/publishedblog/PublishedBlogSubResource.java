package study.huhao.demo.adapters.inbound.rest.resources.publishedblog;

import study.huhao.demo.application.usecases.QueryPublishedBlogUseCase;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import java.util.UUID;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Produces(APPLICATION_JSON)
public class PublishedBlogSubResource {
    private UUID id;
    private QueryPublishedBlogUseCase queryPublishedBlogUseCase;

    PublishedBlogSubResource(UUID id, QueryPublishedBlogUseCase queryPublishedBlogUseCase) {
        this.id = id;
        this.queryPublishedBlogUseCase = queryPublishedBlogUseCase;
    }

    @GET
    public PublishedBlogDto get() {
        return PublishedBlogDto.of(queryPublishedBlogUseCase.get(id));
    }
}
