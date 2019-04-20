package study.huhao.demo.adapters.restapi.resources.blog;

import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import study.huhao.demo.application.services.BlogService;
import study.huhao.demo.domain.models.blog.BlogId;
import study.huhao.demo.domain.models.user.UserId;

@RestController
@RequestMapping(value = "/blogs", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
class BlogResource {

    private final BlogService blogService;
    private final MapperFacade mapper;

    @Autowired
    BlogResource(BlogService blogService, MapperFacade mapper) {
        this.blogService = blogService;
        this.mapper = mapper;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public BlogDto createBlog(@RequestBody BlogCreateRequest data) {
        return mapper.map(
                blogService.createBlog(data.title, data.body, UserId.valueOf(data.authorId)),
                BlogDto.class
        );
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BlogDto getBlog(@PathVariable String id) {
        return mapper.map(
                blogService.getBlog(BlogId.valueOf(id)),
                BlogDto.class
        );
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void saveBlog(@PathVariable String id, @RequestBody BlogSaveRequest data) {
        blogService.saveBlog(BlogId.valueOf(id), data.title, data.body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBlog(@PathVariable String id) {
        blogService.deleteBlog(BlogId.valueOf(id));
    }

    @PostMapping(value = "/{id}/published", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void publishBlog(@PathVariable String id) {
        blogService.publishBlog(BlogId.valueOf(id));
    }
}
