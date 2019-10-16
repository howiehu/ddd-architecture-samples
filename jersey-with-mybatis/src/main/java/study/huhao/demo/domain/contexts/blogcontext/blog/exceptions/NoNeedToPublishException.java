package study.huhao.demo.domain.contexts.blogcontext.blog.exceptions;

import study.huhao.demo.domain.core.common.excpetions.DomainException;

public class NoNeedToPublishException extends DomainException {
    public NoNeedToPublishException() {
        super("no need to publish");
    }
}
