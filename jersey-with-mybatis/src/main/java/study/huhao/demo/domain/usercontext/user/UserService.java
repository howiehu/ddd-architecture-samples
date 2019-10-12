package study.huhao.demo.domain.usercontext.user;

import study.huhao.demo.domain.core.Service;

public class UserService implements Service {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
