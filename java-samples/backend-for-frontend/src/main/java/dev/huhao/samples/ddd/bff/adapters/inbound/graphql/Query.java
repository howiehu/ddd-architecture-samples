package dev.huhao.samples.ddd.bff.adapters.inbound.graphql;

import dev.huhao.samples.ddd.bff.adapters.inbound.graphql.resolvers.blog.Draft;
import dev.huhao.samples.ddd.bff.application.services.DraftService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;

@Component
public class Query implements GraphQLQueryResolver {

    private final DraftService draftService;

    Query(DraftService draftService) {
        this.draftService = draftService;
    }

    Draft draft(String blogId) {
        return draftService.getDraft(blogId);
    }
}
