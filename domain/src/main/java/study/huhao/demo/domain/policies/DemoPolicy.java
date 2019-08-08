package study.huhao.demo.domain.policies;

import study.huhao.demo.domain.core.Policy;
import study.huhao.demo.domain.models.blog.BlogService;
import study.huhao.demo.domain.models.user.UserService;

public class DemoPolicy implements Policy {

    private final BlogService blogService;
    private final UserService userDomainService;

    // The 'Policy' in this sample used to handle the business logic where cross on domain services.
    // TODO: I will implement the demo in the future.
    public DemoPolicy(BlogService blogService, UserService userDomainService) {
        this.blogService = blogService;
        this.userDomainService = userDomainService;
    }
}
