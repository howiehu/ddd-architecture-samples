package dev.huhao.samples.ddd.bff.application.service;

import dev.huhao.samples.ddd.bff.adapters.inbound.graphql.blog.Draft;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class BlogService {
    public Draft getDraft(String blogId) {
        return Draft.builder()
                .blogId(blogId)
                .title("Hello")
                .body("A Nice Day...")
                .createdAt(Instant.now().toString())
                .savedAt(Instant.now().toString())
                .build();
    }
}
