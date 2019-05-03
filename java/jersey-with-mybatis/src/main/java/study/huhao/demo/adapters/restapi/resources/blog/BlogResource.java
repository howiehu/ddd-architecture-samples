package study.huhao.demo.adapters.restapi.resources.blog;

import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import study.huhao.demo.domain.core.Page;
import study.huhao.demo.domain.models.blog.BlogCriteria;
import study.huhao.demo.domain.models.blog.BlogId;
import study.huhao.demo.domain.models.blog.BlogRepository;
import study.huhao.demo.domain.models.blog.BlogService;
import study.huhao.demo.domain.models.user.UserId;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.created;
import static javax.ws.rs.core.Response.status;

@Path("blog")
@Produces(MediaType.APPLICATION_JSON)
@Component
@Transactional
public class BlogResource {
    private final BlogService blogService;

    private final MapperFacade mapper;

    @Autowired
    public BlogResource(BlogRepository blogRepository, MapperFacade mapper) {
        this.blogService = new BlogService(blogRepository);
        this.mapper = mapper;
    }

    @GET
    public Page<BlogDto> allBlog(@QueryParam("limit") int limit, @QueryParam("offset") int offset) {

        var criteria = BlogCriteria.builder()
                .limit(limit)
                .offset(offset)
                .build();

        return blogService.getAllBlog(criteria).map(blog -> mapper.map(blog, BlogDto.class));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBlog(BlogCreateRequest data) {
        var entity = mapper.map(
                blogService.createBlog(data.title, data.body, UserId.valueOf(data.authorId)),
                BlogDto.class
        );

        URI uri = UriBuilder.fromResource(BlogResource.class).path("{id}").build(entity.id);
        return created(uri).entity(entity).build();
    }

    @GET
    @Path("{id}")
    public BlogDto getBlog(@PathParam("id") String id) {
        return mapper.map(
                blogService.getBlog(BlogId.valueOf(id)),
                BlogDto.class
        );
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void saveBlog(@PathParam("id") String id, BlogSaveRequest data) {
        blogService.saveBlog(BlogId.valueOf(id), data.title, data.body);
    }

    @DELETE
    @Path("{id}")
    public void deleteBlog(@PathParam("id") String id) {
        blogService.deleteBlog(BlogId.valueOf(id));
    }

    @POST
    @Path("{id}/published")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response publishBlog(@PathParam("id") String id) {
        blogService.publishBlog(BlogId.valueOf(id));
        return status(CREATED).build();
    }
}
