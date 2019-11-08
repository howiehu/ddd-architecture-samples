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
import static org.mockito.BDDMockito.*;

class BlogServiceTest {

    private BlogRepository blogRepository;

    private BlogService blogService;

    @BeforeEach
    void setUp() {
        blogRepository = mock(BlogRepository.class);
        blogService = new BlogService(blogRepository);
    }

    @Nested
    class create {

        @Test
        void should_create_correctly() {
            var createdUser = blogService
                    .create("Test Blog", "Something...", UUID.randomUUID());

            verify(blogRepository).save(any(Blog.class));
            assertThat(createdUser.getId()).isNotNull();
        }
    }

    @Nested
    class get {

        @Test
        void should_get_correctly() {
            // Given
            var mockBlog = mock(Blog.class);
            given(blogRepository.findById(mockBlog.getId())).willReturn(Optional.of(mockBlog));

            // When
            var foundBlog = blogService.get(mockBlog.getId());

            // Then
            assertThat(foundBlog).isSameAs(mockBlog);
        }

        @Test
        void should_throw_EntityNotFoundException_when_blog_not_found() {
            var mockBlog = mock(Blog.class);

            given(blogRepository.findById(mockBlog.getId())).willReturn(Optional.empty());

            assertThatThrownBy(() -> blogService.get(mockBlog.getId()))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage("cannot find the blog with id " + mockBlog.getId());
        }
    }

    @Nested
    class getPublished {

        @Test
        void should_get_published_blog() {
            var mockPublishedBlog = mock(Blog.class);
            given(mockPublishedBlog.isPublished()).willReturn(true);
            given(blogRepository.findById(mockPublishedBlog.getId())).willReturn(Optional.of(mockPublishedBlog));

            var foundPublishedBlog = blogService.getPublished(mockPublishedBlog.getId());

            assertThat(foundPublishedBlog).isSameAs(mockPublishedBlog);
        }

        @Test
        void should_throw_EntityNotFoundException_when_blog_not_found() {
            var mockPublishedBlog = mock(Blog.class);
            given(blogRepository.findById(mockPublishedBlog.getId())).willReturn(Optional.empty());

            assertThatThrownBy(() -> blogService.getPublished(mockPublishedBlog.getId()))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage("cannot find the published blog with id " + mockPublishedBlog.getId());
        }

        @Test
        void should_throw_EntityNotFoundException_when_published_blog_not_found() {
            var mockPublishedBlog = mock(Blog.class);
            given(mockPublishedBlog.isPublished()).willReturn(false);
            given(blogRepository.findById(mockPublishedBlog.getId())).willReturn(Optional.of(mockPublishedBlog));

            assertThatThrownBy(() -> blogService.getPublished(mockPublishedBlog.getId()))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage("cannot find the published blog with id " + mockPublishedBlog.getId());
        }
    }

    @Nested
    class saveDraft {

        @Test
        void should_save_correctly() {
            var mockBlog = mock(Blog.class);
            given(blogRepository.findById(mockBlog.getId())).willReturn(Optional.of(mockBlog));

            blogService.saveDraft(mockBlog.getId(), "Updated Title", "Updated...");

            InOrder inOrder = inOrder(mockBlog, blogRepository);
            inOrder.verify(mockBlog).saveDraft("Updated Title", "Updated...");
            inOrder.verify(blogRepository).save(mockBlog);
        }

        @Test
        void should_throw_EntityNotFoundException_when_blog_not_found() {
            var mockBlog = mock(Blog.class);
            given(blogRepository.findById(mockBlog.getId())).willReturn(Optional.empty());

            assertThatThrownBy(() -> blogService.saveDraft(mockBlog.getId(), "Updated Title", "Updated..."))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage("cannot find the blog with id " + mockBlog.getId());
        }
    }

    @Nested
    class delete {

        @Test
        void should_delete_correctly() {
            var mockBlog = mock(Blog.class);
            given(blogRepository.existsById(mockBlog.getId())).willReturn(true);

            blogService.delete(mockBlog.getId());

            verify(blogRepository).deleteById(mockBlog.getId());
        }

        @Test
        void should_throw_EntityNotFoundException_when_blog_not_found() {
            var mockBlog = mock(Blog.class);
            given(blogRepository.existsById(mockBlog.getId())).willReturn(false);

            assertThatThrownBy(() -> blogService.delete(mockBlog.getId()))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage("cannot find the blog with id " + mockBlog.getId());
        }
    }

    @Nested
    class publish {

        @Test
        void should_publish_correctly() {
            var mockBlog = mock(Blog.class);
            given(blogRepository.findById(mockBlog.getId())).willReturn(Optional.of(mockBlog));

            Blog createdBlog = blogService.publish(mockBlog.getId());

            InOrder inOrder = inOrder(mockBlog, blogRepository);
            inOrder.verify(mockBlog).publish();
            inOrder.verify(blogRepository).save(mockBlog);

            assertThat(createdBlog).isSameAs(mockBlog);
        }

        @Test
        void should_throw_EntityNotFoundException_when_blog_not_found() {
            var mockBlog = mock(Blog.class);
            given(blogRepository.findById(mockBlog.getId())).willReturn(Optional.empty());

            assertThatThrownBy(() -> blogService.publish(mockBlog.getId()))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage("cannot find the blog with id " + mockBlog.getId());
        }
    }

    @Nested
    class query {

        @Test
        void should_get_all_with_pagination() {
            var mockCriteria = mock(BlogCriteria.class);

            blogService.query(mockCriteria);

            verify(blogRepository).findAllWithPagination(mockCriteria);
        }
    }
}
