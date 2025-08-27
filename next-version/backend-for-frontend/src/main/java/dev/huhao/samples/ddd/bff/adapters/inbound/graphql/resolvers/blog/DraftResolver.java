package dev.huhao.samples.ddd.bff.adapters.inbound.graphql.resolvers.blog;

import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
public class DraftResolver {

    @SchemaMapping(typeName = "Draft")
    public Author author(Draft draft) {
        // TODO: need to receive user data from user service at future.
        return Author.builder().userId(draft.getAuthor().getUserId()).nickname("Alex").build();
    }
}
