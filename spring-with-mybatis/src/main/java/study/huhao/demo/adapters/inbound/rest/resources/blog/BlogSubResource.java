package study.huhao.demo.adapters.inbound.rest.resources.blog;

import study.huhao.demo.application.usecases.EditBlogUseCase;
import study.huhao.demo.application.usecases.QueryBlogUseCase;

import javax.ws.rs.*;
import java.util.UUID;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Produces(APPLICATION_JSON)
public class BlogSubResource {
    private UUID id;
    private QueryBlogUseCase queryBlogUseCase;
    private EditBlogUseCase editBlogUseCase;

    BlogSubResource(UUID id, QueryBlogUseCase queryBlogUseCase, EditBlogUseCase editBlogUseCase) {
        this.id = id;
        this.queryBlogUseCase = queryBlogUseCase;
        this.editBlogUseCase = editBlogUseCase;
    }

    @GET
    public BlogDto get() {
        return BlogDto.of(queryBlogUseCase.get(id));
    }

    @PUT
    @Consumes(APPLICATION_JSON)
    public void put(SaveDraftRequest data) {
        editBlogUseCase.saveDraft(id, data.title, data.body);
    }

    @DELETE
    public void delete() {
        editBlogUseCase.delete(id);
    }
}
