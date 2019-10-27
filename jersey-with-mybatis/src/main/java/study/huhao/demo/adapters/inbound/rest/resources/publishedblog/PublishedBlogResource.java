package study.huhao.demo.adapters.inbound.rest.resources.publishedblog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import study.huhao.demo.application.usecases.EditBlogUseCase;
import study.huhao.demo.application.usecases.QueryPublishedBlogUseCase;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.UUID;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.created;

@Path("published-blog")
@Produces(APPLICATION_JSON)
@Component
public class PublishedBlogResource {

    private final EditBlogUseCase editBlogUseCase;
    private final QueryPublishedBlogUseCase queryPublishedBlogUseCase;

    @Autowired
    public PublishedBlogResource(EditBlogUseCase editBlogUseCase, QueryPublishedBlogUseCase queryPublishedBlogUseCase) {
        this.editBlogUseCase = editBlogUseCase;
        this.queryPublishedBlogUseCase = queryPublishedBlogUseCase;
    }

    @POST
    @Consumes(APPLICATION_JSON)
    public Response post(PublishBlogRequest data) {
        var entity = PublishedBlogDto.of(editBlogUseCase.publish(data.blogId));

        var uri = UriBuilder.fromResource(PublishedBlogResource.class).path("{id}").build(entity.getId());
        return created(uri).entity(entity).build();
    }

    @Path("{id}")
    public PublishedBlogSubResource publishedBlogSubResource(@PathParam("id") UUID id) {
        return new PublishedBlogSubResource(id, queryPublishedBlogUseCase);
    }
}
