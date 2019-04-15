package study.huhao.demo.domain.core.excpetions;

import study.huhao.demo.domain.core.DomainException;

public abstract class DomainServiceException extends RuntimeException implements DomainException {
    public DomainServiceException(String message) {
        super(message);
    }
}
