package study.huhao.demo.adapters.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import study.huhao.demo.adapters.api.requests.BlogCreateRequest;
import study.huhao.demo.application.services.BlogService;
import study.huhao.demo.domain.models.blog.Blog;
import study.huhao.demo.domain.models.user.UserId;

@RestController
@RequestMapping(value = "/blogs", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class BlogController {

    private final BlogService blogService;

    @Autowired
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Blog createBlog(@RequestBody BlogCreateRequest data) {
        return blogService.createBlog(data.getTitle(), data.getBody(), UserId.of(data.getAuthorId()));
    }
}
