package dev.huhao.samples.ddd.blogservice.domain.blogcontext.blog;

import java.util.UUID;

public class BlogDomainService {
    public Blog createDraft(String title, String body, UUID authorId) {
        return new Blog(title, body, authorId);
    }
}
