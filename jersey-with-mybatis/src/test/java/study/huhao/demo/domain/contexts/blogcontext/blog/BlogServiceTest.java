package study.huhao.demo.domain.contexts.blogcontext.blog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import study.huhao.demo.domain.core.common.excpetions.EntityNotFoundException;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BlogServiceTest {

    private BlogRepository blogRepository;

    private BlogService blogService;

    @BeforeEach
    void setUp() {
        blogRepository = mock(BlogRepository.class);
        blogService = new BlogService(blogRepository);
    }

    @Nested
    class createBlog {

        @Test
        void should_create_correctly() {
            var createdUser = blogService
                    .create("Test Blog", "Something...", UUID.randomUUID());

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

            var foundBlog = blogService.get(mockBlog.getId());

            assertThat(foundBlog).isEqualTo(mockBlog);
        }

        @Test
        void should_throw_EntityNotFoundException_when_blog_not_found() {
            var mockBlog = mock(Blog.class);

            when(blogRepository.findById(mockBlog.getId())).thenReturn(Optional.empty());

            assertThatThrownBy(() -> blogService.get(mockBlog.getId()))
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

            blogService.saveDraft(mockBlog.getId(), "Updated Title", "Updated...");

            InOrder inOrder = inOrder(mockBlog, blogRepository);
            inOrder.verify(mockBlog).saveDraft("Updated Title", "Updated...");
            inOrder.verify(blogRepository).save(mockBlog);
        }

        @Test
        void should_throw_EntityNotFoundException_when_blog_not_found() {
            var mockBlog = mock(Blog.class);

            when(blogRepository.findById(mockBlog.getId())).thenReturn(Optional.empty());

            assertThatThrownBy(() -> blogService.saveDraft(mockBlog.getId(), "Updated Title", "Updated..."))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage("cannot find the blog with id " + mockBlog.getId());
        }
    }

    @Nested
    class deleteBlog {

        @Test
        void should_delete_correctly() {
            var mockBlog = mock(Blog.class);
            when(blogRepository.existsById(mockBlog.getId())).thenReturn(true);

            blogService.delete(mockBlog.getId());

            verify(blogRepository).deleteById(mockBlog.getId());
        }

        @Test
        void should_throw_EntityNotFoundException_when_blog_not_found() {
            var mockBlog = mock(Blog.class);

            when(blogRepository.existsById(mockBlog.getId())).thenReturn(false);

            assertThatThrownBy(() -> blogService.delete(mockBlog.getId()))
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

            blogService.publish(mockBlog.getId());

            InOrder inOrder = inOrder(mockBlog, blogRepository);
            inOrder.verify(mockBlog).publish();
            inOrder.verify(blogRepository).save(mockBlog);
        }

        @Test
        void should_throw_EntityNotFoundException_when_blog_not_found() {
            var mockBlog = mock(Blog.class);

            when(blogRepository.findById(mockBlog.getId())).thenReturn(Optional.empty());

            assertThatThrownBy(() -> blogService.publish(mockBlog.getId()))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage("cannot find the blog with id " + mockBlog.getId());
        }
    }

    @Nested
    class getAllBlog {

        @Test
        void should_get_all_with_pagination() {
            var mockCriteria = mock(BlogCriteria.class);

            blogService.query(mockCriteria);

            verify(blogRepository).findAllWithPagination(mockCriteria);
        }
    }
}
