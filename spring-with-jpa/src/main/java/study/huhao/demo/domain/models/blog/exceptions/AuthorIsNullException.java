package study.huhao.demo.domain.models.blog.exceptions;

import study.huhao.demo.domain.core.excpetions.DomainException;

public class AuthorIsNullException extends DomainException {
    public AuthorIsNullException() {
        super("the author cannot be null");
    }
}
