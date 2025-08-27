package dev.huhao.samples.ddd.bff.adapters.inbound.graphql;

import dev.huhao.samples.ddd.bff.adapters.inbound.graphql.resolvers.blog.Draft;
import dev.huhao.samples.ddd.bff.application.services.DraftService;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.stereotype.Controller;

@Controller
public class Query {

    private final DraftService draftService;

    Query(DraftService draftService) {
        this.draftService = draftService;
    }

    @QueryMapping
    Draft draft(@Argument String blogId) {
        return draftService.getDraft(blogId);
    }
}
