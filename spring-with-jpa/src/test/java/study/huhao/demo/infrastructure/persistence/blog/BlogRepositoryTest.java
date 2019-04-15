package study.huhao.demo.infrastructure.persistence.blog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import study.huhao.demo.domain.models.blog.BlogDomainService;
import study.huhao.demo.domain.models.blog.BlogRepository;
import study.huhao.demo.domain.models.user.UserId;
import study.huhao.demo.infrastructure.persistence.RepositoryTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class BlogRepositoryTest extends RepositoryTest {

    @Autowired
    private BlogRepository blogRepository;

    private BlogDomainService blogDomainService;

    @BeforeEach
    void setUp() {
        blogDomainService = new BlogDomainService(blogRepository);
    }

    @Test
    void findById() {
        var blog = blogDomainService
                .createBlog("Test Blog", "Something...", UserId.valueOf(UUID.randomUUID().toString()));

        var foundBlog = blogDomainService.getBlog(blog.getId());

        assertThat(foundBlog.getId()).isEqualTo(blog.getId());
        assertThat(foundBlog.getTitle()).isEqualTo("Test Blog");
        assertThat(foundBlog.getBody()).isEqualTo("Something...");
    }
}
