package study.huhao.demo.adapters.persistence.blog;

import lombok.*;
import study.huhao.demo.adapters.persistence.base.Dto;
import study.huhao.demo.domain.models.blog.BlogDraft;

import javax.persistence.Embeddable;
import java.time.Instant;

@Embeddable
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class BlogDraftDto implements Dto<BlogDraft> {
    private String draftTitle;
    private String draftBody;
    private Instant draftSavedAt;

    public static BlogDraftDto of(BlogDraft draft) {
        return BlogDraftDto.builder()
                .draftTitle(draft.getTitle())
                .draftBody(draft.getBody())
                .draftSavedAt(draft.getSavedAt())
                .build();
    }

    @Override
    public BlogDraft toDomainModel() {
        return new BlogDraft(draftTitle, draftBody, draftSavedAt);
    }
}
