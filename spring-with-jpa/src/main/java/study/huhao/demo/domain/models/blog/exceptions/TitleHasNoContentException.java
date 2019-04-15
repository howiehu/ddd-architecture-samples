package study.huhao.demo.domain.models.blog.exceptions;

import study.huhao.demo.domain.core.excpetions.DomainException;

public class TitleHasNoContentException extends DomainException {
    public TitleHasNoContentException() {
        super("the title cannot be null or no content");
    }
}
