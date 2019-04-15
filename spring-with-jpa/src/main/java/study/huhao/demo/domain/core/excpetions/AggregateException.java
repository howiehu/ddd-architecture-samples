package study.huhao.demo.domain.core.excpetions;

import study.huhao.demo.domain.core.DomainException;

public abstract class AggregateException extends RuntimeException implements DomainException {
    public AggregateException(String message) {
        super(message);
    }
}
