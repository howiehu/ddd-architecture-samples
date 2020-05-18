package dev.huhao.samples.ddd.bff.adapters.inbound.graphql;

import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class Query implements GraphQLQueryResolver {

    Draft draft(String blogId) {
        return new Draft.DraftBuilder()
                .blogId(blogId)
                .title("Hello")
                .body("A Nice Day...")
                .createdAt(Instant.now().toString())
                .savedAt(Instant.now().toString())
                .author(new Author(UUID.randomUUID().toString(), "Alex"))
                .build();
    }
}
