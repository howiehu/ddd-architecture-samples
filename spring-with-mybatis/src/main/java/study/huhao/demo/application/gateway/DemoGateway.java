package study.huhao.demo.application.gateway;

import study.huhao.demo.application.concepts.Gateway;

public interface DemoGateway extends Gateway {
    String retrieveData();
    void sendData();
}
