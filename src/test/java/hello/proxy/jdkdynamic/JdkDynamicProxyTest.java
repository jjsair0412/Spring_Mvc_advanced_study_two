package hello.proxy.jdkdynamic;

import hello.proxy.jdkdynamic.code.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

@Slf4j
public class JdkDynamicProxyTest {

    @Test
    void dynamicA(){
        Ainterface target = new AImpl();

        TimeInvocationHandler handler = new TimeInvocationHandler(target);

        Ainterface proxy = (Ainterface) Proxy.newProxyInstance(Ainterface.class.getClassLoader(), new Class[]{Ainterface.class}, handler);
        //해당 프록시는 handler의 로직을 수행하게 된다.

        proxy.call();
        log.info("targetClass={}",target.getClass());
        log.info("proxyClass={}",proxy.getClass());
    }

    @Test
    void dynamicB(){
        Binterface target = new BImpl();

        TimeInvocationHandler handler = new TimeInvocationHandler(target);

        Binterface proxy = (Binterface) Proxy.newProxyInstance(Binterface.class.getClassLoader(), new Class[]{Binterface.class}, handler);
        //해당 프록시는 handler의 로직을 수행하게 된다.

        proxy.call();
        log.info("targetClass={}",target.getClass());
        log.info("proxyClass={}",proxy.getClass());
    }
}
