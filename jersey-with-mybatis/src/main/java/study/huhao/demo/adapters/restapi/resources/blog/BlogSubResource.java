package study.huhao.demo.adapters.restapi.resources.blog;

import study.huhao.demo.application.BlogEdit;
import study.huhao.demo.application.BlogQuery;

import javax.ws.rs.*;
import java.util.UUID;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Produces(APPLICATION_JSON)
public class BlogSubResource {
    private UUID id;
    private BlogQuery blogQuery;
    private BlogEdit blogEdit;

    BlogSubResource(UUID id, BlogQuery blogQuery, BlogEdit blogEdit) {
        this.id = id;
        this.blogQuery = blogQuery;
        this.blogEdit = blogEdit;
    }

    @GET
    public BlogDto get() {
        return BlogDto.of(blogQuery.get(id));
    }

    @PUT
    @Consumes(APPLICATION_JSON)
    public void put(SaveDraftRequest data) {
        blogEdit.saveDraft(id, data.title, data.body);
    }

    @DELETE
    public void delete() {
        blogEdit.delete(id);
    }
}
