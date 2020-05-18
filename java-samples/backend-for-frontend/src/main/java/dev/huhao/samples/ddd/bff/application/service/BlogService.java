package dev.huhao.samples.ddd.bff.application.service;

import dev.huhao.samples.ddd.bff.adapters.inbound.graphql.blog.Author;
import dev.huhao.samples.ddd.bff.adapters.inbound.graphql.blog.Draft;
import dev.huhao.samples.ddd.bff.adapters.outbound.client.BlogClient;
import dev.huhao.samples.ddd.blogservice.adapters.inbound.grpc.blog.proto.DraftDto;
import org.springframework.stereotype.Service;

@Service
public class BlogService {

    private final BlogClient blogClient;

    public BlogService(BlogClient blogClient) {
        this.blogClient = blogClient;
    }

    public Draft getDraft(String blogId) {

        DraftDto draftDto = blogClient.receiveDraft(blogId);

        return Draft.builder()
                .blogId(draftDto.getBlogId())
                .title(draftDto.getTitle())
                .body(draftDto.getBody())
                .createdAt(draftDto.getCreatedAt())
                .savedAt(draftDto.getSavedAt())
                .author(Author.builder().userId(draftDto.getAuthorId()).build())
                .build();
    }
}
