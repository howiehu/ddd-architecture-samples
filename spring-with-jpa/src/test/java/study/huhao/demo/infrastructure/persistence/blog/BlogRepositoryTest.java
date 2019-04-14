package study.huhao.demo.infrastructure.persistence.blog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import study.huhao.demo.domain.models.blog.BlogDomainService;
import study.huhao.demo.domain.models.blog.BlogRepository;
import study.huhao.demo.domain.models.user.UserId;
import study.huhao.demo.infrastructure.persistence.RepositoryTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class BlogRepositoryTest extends RepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private BlogRepository blogRepository;

    private BlogDomainService blogDomainService;

    @BeforeEach
    void setUp() {
        blogDomainService = new BlogDomainService(blogRepository);
    }

    @Test
    void save() {
        var blog = blogDomainService
                .createBlog("Test Blog", "Something...", UserId.valueOf(UUID.randomUUID().toString()));

        blogRepository.save(blog);

        var blogDto = testEntityManager.find(BlogDto.class, blog.getId().toString());

        assertThat(blogDto.getId()).isEqualTo(blog.getId().toString());
    }

    @Test
    void findById() {
        var blog = blogDomainService
                .createBlog("Test Blog", "Something...", UserId.valueOf(UUID.randomUUID().toString()));
        blogRepository.save(blog);

        var blogDto = blogRepository.findById(blog.getId());

        assertThat(blogDto).isNotEmpty()
                .hasValueSatisfying(dto -> assertThat(dto.getId()).isEqualTo(blog.getId()));
    }
}
