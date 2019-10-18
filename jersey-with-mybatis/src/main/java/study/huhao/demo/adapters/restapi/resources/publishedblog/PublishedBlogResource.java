package study.huhao.demo.adapters.restapi.resources.publishedblog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import study.huhao.demo.application.BlogEdit;
import study.huhao.demo.application.BlogQuery;

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

    private final BlogEdit blogEdit;
    private final BlogQuery blogQuery;

    @Autowired
    public PublishedBlogResource(BlogEdit blogEdit, BlogQuery blogQuery) {
        this.blogEdit = blogEdit;
        this.blogQuery = blogQuery;
    }

    @POST
    @Consumes(APPLICATION_JSON)
    public Response post(PublishRequest data) {
        var entity = PublishedBlogDto.of(blogEdit.publish(data.blogId));

        var uri = UriBuilder.fromResource(PublishedBlogResource.class).path("{id}").build(entity.getId());
        return created(uri).entity(entity).build();
    }

    @Path("{id}")
    public PublishedBlogSubResource publishedBlogSubResource(@PathParam("id") UUID id) {
        return new PublishedBlogSubResource(id, blogQuery);
    }
}
