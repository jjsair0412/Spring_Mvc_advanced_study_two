package hello.proxy.purproxy.concreteproxy;

import hello.proxy.purproxy.concreteproxy.code.ConcreateClient;
import hello.proxy.purproxy.concreteproxy.code.ConcreateLogic;
import org.junit.jupiter.api.Test;

public class ConcreateProxyTest {

    @Test
    void noProxy(){
        ConcreateLogic concreateLogic = new ConcreateLogic();
        ConcreateClient client = new ConcreateClient(concreateLogic);
        client.excute();

    }


}
