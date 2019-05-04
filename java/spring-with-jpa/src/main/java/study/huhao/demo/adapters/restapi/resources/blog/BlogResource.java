package study.huhao.demo.adapters.restapi.resources.blog;

import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import study.huhao.demo.domain.core.Page;
import study.huhao.demo.domain.models.blog.BlogCriteria;
import study.huhao.demo.domain.models.blog.BlogId;
import study.huhao.demo.domain.models.blog.BlogRepository;
import study.huhao.demo.domain.models.blog.BlogService;
import study.huhao.demo.domain.models.user.UserId;

@RestController
@Transactional
@RequestMapping(value = "/blog", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class BlogResource {
    private final BlogService blogService;

    private final MapperFacade mapper;

    @Autowired
    public BlogResource(BlogRepository blogRepository, MapperFacade mapper) {
        this.blogService = new BlogService(blogRepository);
        this.mapper = mapper;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<BlogDto> allBlog(@RequestParam int page, @RequestParam("page_size") int pageSize) {

        var criteria = new BlogCriteria(page, pageSize);

        return blogService.getAllBlog(criteria).map(blog -> mapper.map(blog, BlogDto.class));
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
