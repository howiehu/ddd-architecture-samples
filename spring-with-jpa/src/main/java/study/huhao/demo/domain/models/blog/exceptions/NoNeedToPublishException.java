package study.huhao.demo.domain.models.blog.exceptions;

import study.huhao.demo.domain.core.excpetions.DomainException;

public class NoNeedToPublishException extends DomainException {
    public NoNeedToPublishException() {
        super("no need to publish");
    }
}
