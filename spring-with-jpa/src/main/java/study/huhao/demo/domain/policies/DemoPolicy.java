package study.huhao.demo.domain.policies;

import study.huhao.demo.domain.core.Policy;
import study.huhao.demo.domain.models.blog.BlogDomainService;
import study.huhao.demo.domain.models.user.UserDomainService;

public class DemoPolicy implements Policy {

    private final BlogDomainService blogDomainService;
    private final UserDomainService userDomainService;

    // The 'Policy' in this sample used to handle the business logic where cross on domain services.
    // TODO: I will implement the demo in the future.
    public DemoPolicy(BlogDomainService blogDomainService, UserDomainService userDomainService) {
        this.blogDomainService = blogDomainService;
        this.userDomainService = userDomainService;
    }
}
