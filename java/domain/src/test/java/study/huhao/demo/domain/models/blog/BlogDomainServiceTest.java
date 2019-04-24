package study.huhao.demo.domain.models.blog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import study.huhao.demo.domain.core.excpetions.EntityNotFoundException;
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
            var mockBlog = mock(Blog.class);
            when(blogRepository.findById(mockBlog.getId())).thenReturn(Optional.of(mockBlog));

            var foundBlog = blogDomainService.getBlog(mockBlog.getId());

            assertThat(foundBlog).isEqualTo(mockBlog);
        }

        @Test
        void should_throw_EntityNotFoundException_when_blog_not_found() {
            var mockBlog = mock(Blog.class);

            when(blogRepository.findById(mockBlog.getId())).thenReturn(Optional.empty());

            assertThatThrownBy(() -> blogDomainService.getBlog(mockBlog.getId()))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage("cannot find the blog with id " + mockBlog.getId());
        }
    }

    @Nested
    class saveBlog {

        @Test
        void should_save_correctly() {
            var mockBlog = mock(Blog.class);
            when(blogRepository.findById(mockBlog.getId())).thenReturn(Optional.of(mockBlog));

            blogDomainService.saveBlog(mockBlog.getId(), "Updated Title", "Updated...");

            InOrder inOrder = inOrder(mockBlog, blogRepository);
            inOrder.verify(mockBlog).save("Updated Title", "Updated...");
            inOrder.verify(blogRepository).save(mockBlog);
        }

        @Test
        void should_throw_EntityNotFoundException_when_blog_not_found() {
            var mockBlog = mock(Blog.class);

            when(blogRepository.findById(mockBlog.getId())).thenReturn(Optional.empty());

            assertThatThrownBy(() -> blogDomainService.saveBlog(mockBlog.getId(), "Updated Title", "Updated..."))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage("cannot find the blog with id " + mockBlog.getId());
        }
    }

    @Nested
    class deleteBlog {

        @Test
        void should_delete_correctly() {
            var mockBlog = mock(Blog.class);
            when(blogRepository.existById(mockBlog.getId())).thenReturn(true);

            blogDomainService.deleteBlog(mockBlog.getId());

            verify(blogRepository).deleteById(mockBlog.getId());
        }

        @Test
        void should_throw_EntityNotFoundException_when_blog_not_found() {
            var mockBlog = mock(Blog.class);

            when(blogRepository.existById(mockBlog.getId())).thenReturn(false);

            assertThatThrownBy(() -> blogDomainService.deleteBlog(mockBlog.getId()))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage("cannot find the blog with id " + mockBlog.getId());
        }
    }

    @Nested
    class publishBlog {

        @Test
        void should_delete_correctly() {
            var mockBlog = mock(Blog.class);
            when(blogRepository.findById(mockBlog.getId())).thenReturn(Optional.of(mockBlog));

            blogDomainService.publishBlog(mockBlog.getId());

            InOrder inOrder = inOrder(mockBlog, blogRepository);
            inOrder.verify(mockBlog).publish();
            inOrder.verify(blogRepository).save(mockBlog);
        }

        @Test
        void should_throw_EntityNotFoundException_when_blog_not_found() {
            var mockBlog = mock(Blog.class);

            when(blogRepository.findById(mockBlog.getId())).thenReturn(Optional.empty());

            assertThatThrownBy(() -> blogDomainService.publishBlog(mockBlog.getId()))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage("cannot find the blog with id " + mockBlog.getId());
        }
    }

    @Nested
    class getAllBlog {

        @Test
        void should_get_all_with_pagination() {
            var mockCriteria = mock(BlogCriteria.class);

            blogDomainService.getAllBlog(mockCriteria);

            verify(blogRepository).findAllWithPagination(mockCriteria);
        }
    }
}
