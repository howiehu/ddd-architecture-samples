package study.huhao.demo.application.services;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import study.huhao.demo.domain.models.blog.Blog;
import study.huhao.demo.domain.models.blog.BlogRepository;
import study.huhao.demo.domain.models.user.UserId;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class BlogServiceTest {

    @Nested
    class createBlog {

        @MockBean
        private BlogRepository blogRepository;

        @Test
        void should_create_correctly() {
            var blogService = new BlogService(blogRepository);

            var createdUser = blogService
                    .createBlog("Test Blog", "Something...", UserId.of(UUID.randomUUID().toString()));

            verify(blogRepository).save(any(Blog.class));
            assertThat(createdUser.getId()).isNotNull();
        }
    }
}
