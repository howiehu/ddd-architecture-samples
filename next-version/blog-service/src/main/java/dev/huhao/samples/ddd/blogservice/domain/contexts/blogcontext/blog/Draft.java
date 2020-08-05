package dev.huhao.samples.ddd.blogservice.domain.contexts.blogcontext.blog;

import dev.huhao.samples.ddd.blogservice.domain.concepts.ValueObject;

import java.time.Instant;
import java.util.Objects;

public class Draft implements ValueObject {
    private final String title;
    private final String body;
    private final Instant savedAt;

    public Draft(String title, String body, Instant savedAt) {
        this.title = title;
        this.body = body;
        this.savedAt = savedAt;
    }

    public String getTitle() {
        return this.title;
    }

    public String getBody() {
        return this.body;
    }

    public Instant getSavedAt() {
        return this.savedAt;
    }

    // Value Object 需要实现 equals 和 hashCode 方法
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Draft draft = (Draft) o;
        return Objects.equals(title, draft.title) &&
                Objects.equals(body, draft.body) &&
                Objects.equals(savedAt, draft.savedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, body, savedAt);
    }
}
