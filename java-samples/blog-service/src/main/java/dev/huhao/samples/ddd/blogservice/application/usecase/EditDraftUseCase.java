package dev.huhao.samples.ddd.blogservice.application.usecase;

import dev.huhao.samples.ddd.blogservice.domain.contexts.blogcontext.blog.Blog;
import dev.huhao.samples.ddd.blogservice.domain.contexts.blogcontext.blog.BlogDomainService;
import dev.huhao.samples.ddd.blogservice.domain.contexts.blogcontext.blog.BlogRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
public class EditDraftUseCase {
    private final BlogDomainService blogDomainService;

    public EditDraftUseCase(BlogRepository blogRepository) {
        this.blogDomainService = new BlogDomainService(blogRepository);
    }

    @Transactional
    public Blog createDraft(String title, String body, UUID authorId) {
        return blogDomainService.createDraft(title, body, authorId);
    }

    @Transactional
    public Blog updateDraft(UUID id, String title, String body) {
        return blogDomainService.updateDraft(id, title, body);
    }

    @Transactional
    public Blog publishBlog(UUID id) {
        return blogDomainService.publish(id);
    }
}
