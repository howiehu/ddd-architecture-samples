package study.huhao.demo.adapters.outbound.gateway;

import org.springframework.stereotype.Component;
import study.huhao.demo.application.gateway.DemoGateway;

@Component
public class DemoGatewayImpl implements DemoGateway {
    @Override
    public String retrieveData() {
        return null;
    }

    @Override
    public void sendData() {

    }
}
