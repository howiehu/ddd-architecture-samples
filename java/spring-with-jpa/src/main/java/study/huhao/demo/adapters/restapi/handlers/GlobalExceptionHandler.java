package study.huhao.demo.adapters.restapi.handlers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import study.huhao.demo.domain.core.excpetions.DomainException;
import study.huhao.demo.domain.core.excpetions.EntityExistedException;
import study.huhao.demo.domain.core.excpetions.EntityNotFoundException;
import study.huhao.demo.domain.models.blog.exceptions.NoNeedToPublishException;

import java.util.Map;

@ControllerAdvice
class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
        return handleDomainException(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({EntityExistedException.class})
    public ResponseEntity<Object> handleEntityExistedException(EntityExistedException ex) {
        return handleDomainException(ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({NoNeedToPublishException.class})
    public ResponseEntity<Object> handleNoNeedToPublishException(NoNeedToPublishException ex) {
        return handleDomainException(ex, HttpStatus.CONFLICT);
    }

    private ResponseEntity<Object> handleDomainException(DomainException ex, HttpStatus conflict) {
        var body = Map.of("message", ex.getMessage());
        return new ResponseEntity<>(body, new HttpHeaders(), conflict);
    }
}
