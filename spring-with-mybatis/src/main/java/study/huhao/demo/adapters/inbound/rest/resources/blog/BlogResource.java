package study.huhao.demo.adapters.inbound.rest.resources.blog;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import study.huhao.demo.application.dto.Page;
import study.huhao.demo.application.usecases.EditBlogUseCase;
import study.huhao.demo.application.usecases.QueryBlogUseCase;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/blog", produces = APPLICATION_JSON_VALUE)
public class BlogResource {

    private final QueryBlogUseCase queryBlogUseCase;
    private final EditBlogUseCase editBlogUseCase;

    public BlogResource(QueryBlogUseCase queryBlogUseCase, EditBlogUseCase editBlogUseCase) {
        this.queryBlogUseCase = queryBlogUseCase;
        this.editBlogUseCase = editBlogUseCase;
    }

    @GetMapping
    public Page<BlogDto> get(@RequestParam int limit, @RequestParam int offset) {
        return queryBlogUseCase.query(limit, offset).map(BlogDto::of);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> post(@RequestBody CreateBlogRequest data, UriComponentsBuilder uriComponentsBuilder) {
        var blog = editBlogUseCase.create(data.title, data.body, UUID.fromString(data.authorId));
        var uriComponents = uriComponentsBuilder.path("/blog/{id}").buildAndExpand(blog.getId());
        return ResponseEntity.created(uriComponents.toUri()).body(BlogDto.of(blog));
    }
}
