package study.huhao.name.springwithjpa.adapters.persistence.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import study.huhao.name.springwithjpa.domain.models.blog.BlogDraft;

import javax.persistence.Embeddable;
import java.time.Instant;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogDraftDto {
    private String draftTitle;
    private String draftBody;
    private Instant draftSavedAt;

    public BlogDraft toEntity() {
        return new BlogDraft(draftTitle, draftBody, draftSavedAt);
    }

    public static BlogDraftDto of(BlogDraft draft) {
        return BlogDraftDto.builder()
                .draftTitle(draft.getTitle())
                .draftBody(draft.getBody())
                .draftSavedAt(draft.getSavedAt())
                .build();
    }
}
