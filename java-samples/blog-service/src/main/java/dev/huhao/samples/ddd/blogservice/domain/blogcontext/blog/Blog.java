package dev.huhao.samples.ddd.blogservice.domain.blogcontext.blog;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class Blog {
    private final UUID blogId;
    private String title;
    private String body;
    private final UUID authorId;
    private final Instant createdAt;
    private Instant savedAt;

    Blog(String title, String body, UUID authorId) {
        this.blogId = UUID.randomUUID();
        this.title = title;
        this.body = body;
        this.authorId = authorId;
        // FIXME: Java的Instant精度（纳秒）与MySQL的Timestamp精度（微秒）不一致，会导致从数据库取出的Instant精度降低。
        this.savedAt = this.createdAt = Instant.now();
    }
}
