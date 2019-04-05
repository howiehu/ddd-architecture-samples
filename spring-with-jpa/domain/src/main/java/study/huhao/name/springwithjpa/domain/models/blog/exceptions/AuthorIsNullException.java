package study.huhao.name.springwithjpa.domain.models.blog.exceptions;

public class AuthorIsNullException extends RuntimeException {
    public AuthorIsNullException() {
        super("the author cannot be null");
    }
}
