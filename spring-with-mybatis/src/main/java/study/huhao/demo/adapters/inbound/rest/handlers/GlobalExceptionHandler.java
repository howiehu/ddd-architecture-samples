package study.huhao.demo.adapters.inbound.rest.handlers;

import com.google.common.collect.ImmutableMap;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import study.huhao.demo.domain.contexts.blogcontext.blog.exceptions.NoNeedToPublishException;
import study.huhao.demo.domain.core.common.excpetions.DomainException;
import study.huhao.demo.domain.core.common.excpetions.EntityExistedException;
import study.huhao.demo.domain.core.common.excpetions.EntityNotFoundException;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
        return handleDomainException(ex, NOT_FOUND);
    }

    @ExceptionHandler({EntityExistedException.class})
    public ResponseEntity<Object> handleEntityExistedException(EntityExistedException ex) {
        return handleDomainException(ex, CONFLICT);
    }

    @ExceptionHandler({NoNeedToPublishException.class})
    public ResponseEntity<Object> handleNoNeedToPublishException(NoNeedToPublishException ex) {
        return handleDomainException(ex, CONFLICT);
    }

    private ResponseEntity<Object> handleDomainException(DomainException ex, HttpStatus conflict) {
        return new ResponseEntity<>(ImmutableMap.of("message", ex.getMessage()), new HttpHeaders(), conflict);
    }
}
