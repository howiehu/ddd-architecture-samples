package dev.huhao.samples.ddd.bff.adapters.inbound.graphql.blog;

import graphql.kickstart.tools.GraphQLResolver;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DraftResolver implements GraphQLResolver<Draft> {

    public Author getAuthor(Draft draft) {
        return Author.builder().userId(UUID.randomUUID().toString()).nickname("Alex").build();
    }
}
