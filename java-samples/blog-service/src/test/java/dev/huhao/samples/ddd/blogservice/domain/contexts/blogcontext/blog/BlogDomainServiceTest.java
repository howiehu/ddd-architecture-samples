package dev.huhao.samples.ddd.blogservice.domain.contexts.blogcontext.blog;

import dev.huhao.samples.ddd.blogservice.domain.common.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
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
    class createDraft {

        @Test
        void should_save_by_repository() {
            UUID authorId = UUID.randomUUID();

            Blog blog = blogDomainService.createDraft("Hello", "A nice day...", authorId);

            verify(blogRepository).save(any(Blog.class));

            assertThat(blog.getDraft().getTitle()).isEqualTo("Hello");
            assertThat(blog.getDraft().getBody()).isEqualTo("A nice day...");
            assertThat(blog.getAuthorId()).isEqualTo(authorId);
        }
    }

    @Nested
    class getBlog {

        @Test
        void should_return_blog_with_same_id() {
            UUID blogId = UUID.randomUUID();

            Blog stubBlog = mock(Blog.class);
            given(blogRepository.findById(blogId)).willReturn(Optional.of(stubBlog));

            Blog result = blogDomainService.getBlog(blogId);

            assertThat(result).isSameAs(stubBlog);
        }

        @Test
        void should_throw_EntityNotFoundException_when_not_found() {
            UUID blogId = UUID.randomUUID();

            given(blogRepository.findById(blogId)).willReturn(Optional.empty());

            assertThatThrownBy(() -> blogDomainService.getBlog(blogId))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage("cannot find the blog with id " + blogId);
        }
    }

    @Nested
    class updateDraft {

        @Test
        void should_return_updated_blog() {
            UUID blogId = UUID.randomUUID();

            Blog stubBlog = mock(Blog.class);
            given(blogRepository.findById(blogId)).willReturn(Optional.of(stubBlog));

            Blog result = blogDomainService.updateDraft(blogId, "Hi", "Great!");

            InOrder inOrder = inOrder(stubBlog, blogRepository);
            inOrder.verify(stubBlog).updateDraft("Hi", "Great!");
            inOrder.verify(blogRepository).save(stubBlog);

            assertThat(result).isSameAs(stubBlog);
        }

        @Test
        void should_throw_EntityNotFoundException_when_not_found() {
            UUID blogId = UUID.randomUUID();

            given(blogRepository.findById(blogId)).willReturn(Optional.empty());

            assertThatThrownBy(() -> blogDomainService.updateDraft(blogId, "Hi", "Great!"))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage("cannot find the blog with id " + blogId);
        }
    }

    @Nested
    class publish {

        @Test
        void should_return_published_blog() {
            UUID blogId = UUID.randomUUID();

            Blog stubBlog = mock(Blog.class);
            given(blogRepository.findById(blogId)).willReturn(Optional.of(stubBlog));

            Blog result = blogDomainService.publish(blogId);

            InOrder inOrder = inOrder(stubBlog, blogRepository);
            inOrder.verify(stubBlog).publish();
            inOrder.verify(blogRepository).save(stubBlog);

            assertThat(result).isSameAs(stubBlog);
        }

        @Test
        void should_throw_EntityNotFoundException_when_not_found() {
            UUID blogId = UUID.randomUUID();

            given(blogRepository.findById(blogId)).willReturn(Optional.empty());

            assertThatThrownBy(() -> blogDomainService.publish(blogId))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage("cannot find the blog with id " + blogId);
        }
    }

    @Nested
    class deleteBlog {

        @Test
        void should_find_and_delete() {
            UUID blogId = UUID.randomUUID();
            given(blogRepository.existsById(blogId)).willReturn(true);

            blogDomainService.deleteBlog(blogId);

            verify(blogRepository).deleteById(blogId);
        }

        @Test
        void should_throw_EntityNotFoundException_when_not_found() {
            UUID blogId = UUID.randomUUID();
            given(blogRepository.existsById(blogId)).willReturn(false);

            assertThatThrownBy(() -> blogDomainService.deleteBlog(blogId))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage("cannot find the blog with id " + blogId);

            verify(blogRepository, never()).deleteById(blogId);
        }
    }
}
