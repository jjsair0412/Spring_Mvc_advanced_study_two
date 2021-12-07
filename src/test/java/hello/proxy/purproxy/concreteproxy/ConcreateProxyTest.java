package hello.proxy.purproxy.concreteproxy;

import hello.proxy.purproxy.concreteproxy.code.ConcreateClient;
import hello.proxy.purproxy.concreteproxy.code.ConcreateLogic;
import hello.proxy.purproxy.concreteproxy.code.TimeProxy;
import org.junit.jupiter.api.Test;

public class ConcreateProxyTest {

    @Test
    void noProxy(){
        ConcreateLogic concreateLogic = new ConcreateLogic();
        ConcreateClient client = new ConcreateClient(concreateLogic);
        client.excute();

    }


    @Test
    void addProxy(){
        ConcreateLogic concreateLogic = new ConcreateLogic();
        TimeProxy timeProxy = new TimeProxy(concreateLogic);
        ConcreateClient client = new ConcreateClient(timeProxy);

        client.excute();
        // 인터페이스가아닌 구체클래스에서 프록시를 도입하는 방법은,
        // 상속을 받아서 상위 클래스를 같은 상위클래스를 가지게끔 만드는 방법으로 하면된다.
    }
}
