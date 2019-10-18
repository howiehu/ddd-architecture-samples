package study.huhao.demo.adapters.restapi.resources.publishedblog;

import study.huhao.demo.application.BlogQuery;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import java.util.UUID;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Produces(APPLICATION_JSON)
public class PublishedBlogSubResource {
    private UUID id;
    private BlogQuery blogQuery;

    PublishedBlogSubResource(UUID id, BlogQuery blogQuery) {
        this.id = id;
        this.blogQuery = blogQuery;
    }

    @GET
    public PublishedBlogDto get() {
        return PublishedBlogDto.of(blogQuery.get(id));
    }
}
