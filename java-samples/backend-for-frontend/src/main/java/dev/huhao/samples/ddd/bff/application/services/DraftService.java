package dev.huhao.samples.ddd.bff.application.services;

import dev.huhao.samples.ddd.bff.adapters.inbound.graphql.resolvers.blog.Author;
import dev.huhao.samples.ddd.bff.adapters.inbound.graphql.resolvers.blog.Draft;
import dev.huhao.samples.ddd.bff.adapters.outbound.gateway.DraftGateway;
import dev.huhao.samples.ddd.protobuf.blog.Draft.DraftDto;
import org.springframework.stereotype.Service;

@Service
public class DraftService {

    private final DraftGateway draftGateway;

    public DraftService(DraftGateway draftGateway) {
        this.draftGateway = draftGateway;
    }

    public Draft getDraft(String blogId) {
        return buildDraft(draftGateway.getDraft(blogId));
    }

    public Draft createDraft(String title, String body, String authorId) {
        return buildDraft(draftGateway.createDraft(title, body, authorId));
    }

    private Draft buildDraft(DraftDto draftDto) {
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
