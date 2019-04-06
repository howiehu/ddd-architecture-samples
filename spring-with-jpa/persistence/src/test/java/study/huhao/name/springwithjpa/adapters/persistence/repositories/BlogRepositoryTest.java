package study.huhao.name.springwithjpa.adapters.persistence.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import study.huhao.name.springwithjpa.adapters.persistence.dtos.BlogDto;
import study.huhao.name.springwithjpa.domain.models.blog.Blog;
import study.huhao.name.springwithjpa.domain.models.blog.BlogRepository;
import study.huhao.name.springwithjpa.domain.models.user.UserId;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class BlogRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BlogRepository blogRepository;

    @Test
    void save() {
        var blog = new Blog("Test Blog", "Something...", UserId.of(UUID.randomUUID().toString()));

        blogRepository.save(blog);

        var createdBlogDto = entityManager.find(BlogDto.class, blog.getId().toString());

        assertThat(createdBlogDto.getId()).isEqualTo(blog.getId().toString());
    }
}
