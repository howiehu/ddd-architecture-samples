package dev.huhao.samples.ddd.blogservice.domain.blogcontext.blog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    @Test
    void create_draft_should_save_by_repository() {
        // Given
        UUID authorId = UUID.randomUUID();

        // When
        Blog draft = blogDomainService.createDraft("Hello", "A nice day...", authorId);

        // Then
        verify(blogRepository).save(any(Blog.class));

        assertThat(draft.getTitle()).isEqualTo("Hello");
        assertThat(draft.getBody()).isEqualTo("A nice day...");
        assertThat(draft.getAuthorId()).isEqualTo(authorId);
    }
}
