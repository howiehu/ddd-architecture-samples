package study.huhao.demo.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.huhao.demo.domain.contexts.blogcontext.blog.Blog;
import study.huhao.demo.domain.contexts.blogcontext.blog.BlogRepository;
import study.huhao.demo.domain.contexts.blogcontext.blog.BlogService;

import java.util.UUID;

@Service
public class BlogEdit {

    private final BlogService blogService;

    @Autowired
    public BlogEdit(BlogRepository blogRepository) {
        this.blogService = new BlogService(blogRepository);
    }

    @Transactional
    public Blog create(String title, String body, UUID authorId) {
        return blogService.create(title, body, authorId);
    }

    @Transactional
    public void delete(UUID id) {
        blogService.delete(id);
    }

    @Transactional
    public void saveDraft(UUID id, String title, String body) {
        blogService.saveDraft(id, title, body);
    }

    @Transactional
    public void publish(UUID id) {
        blogService.publish(id);
    }
}
