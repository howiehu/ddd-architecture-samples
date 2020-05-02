package dev.huhao.samples.ddd.blogservice.application.usecase;

import dev.huhao.samples.ddd.blogservice.domain.blogcontext.blog.Blog;
import dev.huhao.samples.ddd.blogservice.domain.blogcontext.blog.BlogDomainService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
public class EditBlogUseCase {
    private final BlogDomainService blogDomainService;

    public EditBlogUseCase() {
        this.blogDomainService = new BlogDomainService();
    }

    @Transactional
    public Blog createDraft(String title, String body, UUID authorId) {
        return blogDomainService.createDraft(title, body, authorId);
    }
}
