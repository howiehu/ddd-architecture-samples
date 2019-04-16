package study.huhao.demo.domain.models.blog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import study.huhao.demo.domain.core.AggregateRoot;
import study.huhao.demo.domain.core.ValueObject;
import study.huhao.demo.domain.models.blog.exceptions.NoNeedToPublishException;
import study.huhao.demo.domain.models.user.UserId;

import java.time.Instant;

@AllArgsConstructor
@Getter
public class Blog implements AggregateRoot {
    private BlogId id;
    private String title;
    private String body;
    private UserId authorId;
    private PublishStatus status;
    private Instant createdAt;
    private Instant savedAt;
    private PublishedBlog published;

    Blog(String title, String body, UserId authorId) {
        validateTitle(title);
        validateAuthor(authorId);

        this.id = new BlogId();
        this.title = title;
        this.body = body;
        this.authorId = authorId;
        this.status = PublishStatus.Draft;
        this.createdAt = Instant.now();
        this.savedAt = this.createdAt;
    }

    void publish() {
        validateIsNeedToPublish();

        this.published = new PublishedBlog(this.title, this.body, Instant.now());
        this.status = PublishStatus.Published;
    }

    void save(String title, String body) {
        validateTitle(title);

        this.title = title;
        this.body = body;
        this.savedAt = Instant.now();
    }

    private void validateAuthor(UserId author) {
        if (author == null) {
            throw new IllegalArgumentException("the author cannot be null");
        }
    }

    private void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("the title cannot be null or no content");
        }
    }

    private void validateIsNeedToPublish() {
        if (this.status == PublishStatus.Published) {
            boolean noChange =
                    this.title.equals(this.published.getTitle()) && this.body.equals(this.published.getBody());
            if (noChange) {
                throw new NoNeedToPublishException();
            }
        }
    }

    public enum PublishStatus implements ValueObject {
        Draft,
        Published
    }

}
