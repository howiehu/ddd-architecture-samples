package study.huhao.demo.domain.models.blog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import study.huhao.demo.domain.models.blog.exceptions.BlogNotFoundException;
import study.huhao.demo.domain.models.user.UserId;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
                    .createBlog("Test Blog", "Something...", UserId.valueOf(UUID.randomUUID().toString()));

            verify(blogRepository).save(any(Blog.class));
            assertThat(createdUser.getId()).isNotNull();
        }
    }

    @Nested
    class getBlog {

        @Test
        void should_get_correctly() {
            var createdBlog =
                    new Blog("Test Blog", "Something...", UserId.valueOf(UUID.randomUUID().toString()));

            when(blogRepository.findById(createdBlog.getId())).thenReturn(Optional.of(createdBlog));

            var foundBlog = blogDomainService.getBlog(createdBlog.getId());

            assertThat(foundBlog.getId()).isEqualTo(createdBlog.getId());
        }

        @Test
        void should_throw_BlogNotFoundException_when_blog_not_found() {
            var blogId = BlogId.valueOf(UUID.randomUUID().toString());

            when(blogRepository.findById(blogId)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> blogDomainService.getBlog(blogId))
                    .isInstanceOf(BlogNotFoundException.class)
                    .hasMessage("cannot find the blog with id " + blogId);
        }
    }
}
