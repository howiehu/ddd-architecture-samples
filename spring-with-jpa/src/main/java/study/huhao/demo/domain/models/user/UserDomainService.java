package study.huhao.demo.domain.models.user;

import study.huhao.demo.domain.core.DomainService;

public class UserDomainService implements DomainService {

    private final UserRepository userRepository;

    public UserDomainService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
