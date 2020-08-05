package dev.huhao.samples.ddd.bff.adapters.inbound.graphql.resolvers.blog;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Draft {
    private String blogId;
    private String title;
    private String body;
    private String createdAt;
    private String savedAt;
    private Author author;
}
