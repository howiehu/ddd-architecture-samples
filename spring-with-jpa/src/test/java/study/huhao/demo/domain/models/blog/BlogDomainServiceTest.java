package study.huhao.demo.domain.models.blog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import study.huhao.demo.domain.models.user.UserId;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class BlogDomainServiceTest {

    private BlogRepository blogRepository;

    private BlogDomainService blogDomainService;

    @BeforeEach
    void setUp() {
        blogRepository = mock(BlogRepository.class);
        blogDomainService = new BlogDomainService(blogRepository);
    }

    @Nested
    class createBlog {

        @Test
        void should_create_correctly() {
            var createdUser = blogDomainService
                    .createBlog("Test Blog", "Something...", UserId.of(UUID.randomUUID().toString()));

            verify(blogRepository).save(any(Blog.class));
            assertThat(createdUser.getId()).isNotNull();
        }
    }
}
