package study.huhao.demo.adapters.restapi.resources.blog;

import ma.glasnost.orika.MapperFacade;
import study.huhao.demo.application.BlogEdit;
import study.huhao.demo.application.BlogQuery;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.status;

@Produces(MediaType.APPLICATION_JSON)
public class BlogSubResource {
    private UUID id;
    private BlogQuery blogQuery;
    private BlogEdit blogEdit;
    private MapperFacade mapper;

    BlogSubResource(UUID id, BlogQuery blogQuery, BlogEdit blogEdit, MapperFacade mapper) {
        this.id = id;
        this.blogQuery = blogQuery;
        this.blogEdit = blogEdit;
        this.mapper = mapper;
    }

    @GET
    public BlogDto get() {
        return mapper.map(blogQuery.get(id), BlogDto.class);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void put(BlogSaveRequest data) {
        blogEdit.saveDraft(id, data.title, data.body);
    }

    @DELETE
    public void delete() {
        blogEdit.delete(id);
    }

    @POST
    @Path("published")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response publish() {
        blogEdit.publish(id);
        return status(CREATED).build();
    }
}
