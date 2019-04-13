package study.huhao.demo.infrastructure.persistence.blog;

import org.flywaydb.test.FlywayTestExecutionListener;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import study.huhao.demo.domain.models.blog.BlogDomainService;
import study.huhao.demo.domain.models.blog.BlogRepository;
import study.huhao.demo.domain.models.user.UserId;

import javax.persistence.EntityManager;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class})
@FlywayTest
@Transactional
class BlogRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private BlogRepository blogRepository;

    private BlogDomainService blogDomainService;

    @BeforeEach
    void setUp() {
        blogDomainService = new BlogDomainService(blogRepository);
    }

    @Test
    void save() {
        var blog = blogDomainService.createBlog("Test Blog", "Something...", UserId.of(UUID.randomUUID().toString()));

        blogRepository.save(blog);

        var blogPO = entityManager.find(BlogPO.class, blog.getId().toString());

        assertThat(blogPO.getId()).isEqualTo(blog.getId().toString());
    }
}
