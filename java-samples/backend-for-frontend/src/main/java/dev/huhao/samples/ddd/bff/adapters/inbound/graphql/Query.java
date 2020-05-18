package dev.huhao.samples.ddd.bff.adapters.inbound.graphql;

import dev.huhao.samples.ddd.bff.adapters.inbound.graphql.blog.Draft;
import dev.huhao.samples.ddd.bff.application.service.BlogService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;

@Component
public class Query implements GraphQLQueryResolver {

    private final BlogService blogService;

    public Query(BlogService blogService) {
        this.blogService = blogService;
    }

    Draft draft(String blogId) {
        return blogService.getDraft(blogId);
    }
}
