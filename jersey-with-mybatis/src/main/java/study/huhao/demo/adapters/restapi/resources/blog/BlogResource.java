package study.huhao.demo.adapters.restapi.resources.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import study.huhao.demo.application.BlogEdit;
import study.huhao.demo.application.BlogQuery;
import study.huhao.demo.domain.core.common.Page;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.UUID;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.created;

@Path("blog")
@Produces(APPLICATION_JSON)
@Component
public class BlogResource {

    private final BlogQuery blogQuery;
    private final BlogEdit blogEdit;

    @Autowired
    public BlogResource(BlogQuery blogQuery, BlogEdit blogEdit) {
        this.blogQuery = blogQuery;
        this.blogEdit = blogEdit;
    }

    @GET
    public Page<BlogDto> get(@QueryParam("limit") int limit, @QueryParam("offset") int offset) {
        return blogQuery.query(limit, offset).map(BlogDto::of);
    }

    @POST
    @Consumes(APPLICATION_JSON)
    public Response post(CreateBlogRequest data) {
        var blog = blogEdit.create(data.title, data.body, UUID.fromString(data.authorId));

        var uri = UriBuilder.fromResource(BlogResource.class).path("{id}").build(blog.getId());
        return created(uri).entity(BlogDto.of(blog)).build();
    }

    @Path("{id}")
    public BlogSubResource blogSubResource(@PathParam("id") UUID id) {
        return new BlogSubResource(id, blogQuery, blogEdit);
    }
}
