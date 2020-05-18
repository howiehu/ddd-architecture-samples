package dev.huhao.samples.ddd.bff.adapters.inbound.graphql.blog;

import graphql.kickstart.tools.GraphQLResolver;
import org.springframework.stereotype.Component;

@Component
public class DraftResolver implements GraphQLResolver<Draft> {

    public Author getAuthor(Draft draft) {
        // TODO: need to receive user data from user service at future.
        return Author.builder().userId(draft.getAuthor().getUserId()).nickname("Alex").build();
    }
}
