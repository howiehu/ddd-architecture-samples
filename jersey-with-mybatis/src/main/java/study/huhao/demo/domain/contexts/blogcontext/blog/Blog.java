package study.huhao.demo.domain.contexts.blogcontext.blog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import study.huhao.demo.domain.contexts.blogcontext.blog.exceptions.NoNeedToPublishException;
import study.huhao.demo.domain.core.concepts.AggregateRoot;
import study.huhao.demo.domain.core.concepts.ValueObject;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class Blog implements AggregateRoot {
    private UUID id;
    private String title;
    private String body;
    private UUID authorId;
    private Status status;
    private Instant createdAt;
    private Instant savedAt;
    private PublishedBlog published;

    Blog(String title, String body, UUID authorId) {
        validateTitle(title);
        validateAuthor(authorId);

        this.id = UUID.randomUUID();
        this.title = title;
        this.body = body;
        this.authorId = authorId;
        this.status = Status.Draft;
        this.savedAt = this.createdAt = Instant.now();
    }

    public PublishedBlog getPublished() {
        // return immutable object to follow the encapsulation principle
        return this.published == null ? null : new PublishedBlog(
                this.published.getTitle(),
                this.published.getBody(),
                this.published.getPublishedAt());
    }

    void publish() {
        validateIsNeedToPublish();

        this.published = new PublishedBlog(this.title, this.body, Instant.now());
        this.status = Status.Published;
    }

    void save(String title, String body) {
        validateTitle(title);

        this.title = title;
        this.body = body;
        this.savedAt = Instant.now();
    }

    private void validateAuthor(UUID author) {
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
        if (this.status == Status.Published) {
            boolean noChange =
                    this.title.equals(this.published.getTitle()) && this.body.equals(this.published.getBody());
            if (noChange) {
                throw new NoNeedToPublishException();
            }
        }
    }

    public enum Status implements ValueObject {
        Draft,
        Published
    }

}
