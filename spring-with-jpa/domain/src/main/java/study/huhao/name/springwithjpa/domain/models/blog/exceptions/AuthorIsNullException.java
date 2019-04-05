package study.huhao.name.springwithjpa.domain.models.blog.exceptions;

import study.huhao.name.springwithjpa.domain.models.base.excpetions.AggregateException;

public class AuthorIsNullException extends AggregateException {
    public AuthorIsNullException() {
        super("the author cannot be null");
    }
}
