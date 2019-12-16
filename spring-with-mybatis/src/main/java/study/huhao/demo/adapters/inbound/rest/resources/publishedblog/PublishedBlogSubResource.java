package study.huhao.demo.adapters.inbound.rest.resources.publishedblog;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.huhao.demo.application.usecases.QueryPublishedBlogUseCase;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/published-blog/{id}", produces = APPLICATION_JSON_VALUE)
public class PublishedBlogSubResource {
    private QueryPublishedBlogUseCase queryPublishedBlogUseCase;

    public PublishedBlogSubResource(QueryPublishedBlogUseCase queryPublishedBlogUseCase) {
        this.queryPublishedBlogUseCase = queryPublishedBlogUseCase;
    }

    @GetMapping
    public PublishedBlogDto get(@PathVariable UUID id) {
        return PublishedBlogDto.of(queryPublishedBlogUseCase.get(id));
    }
}
