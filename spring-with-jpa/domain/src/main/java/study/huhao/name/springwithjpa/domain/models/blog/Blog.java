package study.huhao.name.springwithjpa.domain.models.blog;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import study.huhao.name.springwithjpa.domain.models.base.AggregateRoot;
import study.huhao.name.springwithjpa.domain.models.blog.exceptions.AuthorIsNullException;
import study.huhao.name.springwithjpa.domain.models.blog.exceptions.NoNeedToPublishException;
import study.huhao.name.springwithjpa.domain.models.blog.exceptions.TitleHasNoContentException;
import study.huhao.name.springwithjpa.domain.models.user.UserId;

import java.time.Instant;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Blog implements AggregateRoot {
    private BlogId id;
    private String title;
    private String body;
    private UserId author;
    private PublishStatus status;
    private Instant createdAt;
    private Instant publishedAt;
    private Instant lastModifiedAt;
    private BlogDraft draft;

    public Blog(String title, String body, UserId author) {

        validateTitle(title);
        validateAuthor(author);

        this.id = new BlogId();
        this.author = author;
        this.status = PublishStatus.Draft;
        this.createdAt = Instant.now();

        saveDraft(title, body, this.createdAt);
    }

    public void publish() {

        validateIsNeedToPublish();

        this.title = this.draft.getTitle();
        this.body = this.draft.getBody();
        this.draft = null;
        this.status = PublishStatus.Published;

        if (this.publishedAt == null) {
            this.publishedAt = Instant.now();
        } else {
            this.lastModifiedAt = Instant.now();
        }
    }

    public void save(String title, String body) {
        validateTitle(title);
        saveDraft(title, body, Instant.now());
    }

    private void validateAuthor(UserId author) {
        if (author == null) {
            throw new AuthorIsNullException();
        }
    }

    private void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new TitleHasNoContentException();
        }
    }

    private void validateIsNeedToPublish() {
        if (this.draft == null) {
            throw new NoNeedToPublishException();
        }

        if (this.status == PublishStatus.Published) {
            boolean noChange =
                    this.title.equals(this.draft.getTitle()) && this.body.equals(this.draft.getBody());
            if (noChange) {
                throw new NoNeedToPublishException();
            }
        }
    }

    private void saveDraft(String title, String body, Instant savedAt) {
        this.draft = new BlogDraft(this.id, title, body, savedAt);
    }

    public enum PublishStatus {
        Draft,
        Published
    }

}
