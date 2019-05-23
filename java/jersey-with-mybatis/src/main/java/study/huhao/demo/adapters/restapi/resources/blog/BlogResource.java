package study.huhao.demo.adapters.restapi.resources.blog;

import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import study.huhao.demo.domain.core.Page;
import study.huhao.demo.domain.models.blog.BlogCriteria;
import study.huhao.demo.domain.models.blog.BlogRepository;
import study.huhao.demo.domain.models.blog.BlogService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.UUID;

import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.created;
import static javax.ws.rs.core.Response.status;

@Path("blog")
@Produces(MediaType.APPLICATION_JSON)
@Component
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
    @Transactional
    public Response createBlog(BlogCreateRequest data) {
        var entity = mapper.map(
                blogService.createBlog(data.title, data.body, UUID.fromString(data.authorId)),
                BlogDto.class
        );

        URI uri = UriBuilder.fromResource(BlogResource.class).path("{id}").build(entity.id);
        return created(uri).entity(entity).build();
    }

    @GET
    @Path("{id}")
    public BlogDto getBlog(@PathParam("id") UUID id) {
        return mapper.map(
                blogService.getBlog(id),
                BlogDto.class
        );
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public void saveBlog(@PathParam("id") UUID id, BlogSaveRequest data) {
        blogService.saveBlog(id, data.title, data.body);
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public void deleteBlog(@PathParam("id") UUID id) {
        blogService.deleteBlog(id);
    }

    @POST
    @Path("{id}/published")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response publishBlog(@PathParam("id") UUID id) {
        blogService.publishBlog(id);
        return status(CREATED).build();
    }
}
