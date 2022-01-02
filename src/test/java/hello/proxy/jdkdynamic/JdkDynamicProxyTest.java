package hello.proxy.jdkdynamic;

import hello.proxy.jdkdynamic.code.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

@Slf4j
public class JdkDynamicProxyTest {

    @Test
    void dynamicA(){
        // interface를 객체로 만들고 , 구현체를 통해 인스턴스화 시켜준다.
        Ainterface target = new AImpl();

        // 아까 jdk 동적프록시를 생성할 handler를 인스턴스화 한다.
        // 생성될때 인자값으로 프록시설정 대상이 들어가기에 , 위에 만들어주었던 Ainterface 객체인 target을 넣어준다.
        TimeInvocationHandler handler = new TimeInvocationHandler(target);

        /**
         *          Proxy.newProxyInstance()를 통해 동적프록시를 생성시켜준다.
         *          Proxy.newProxyInstance()의 인자값은 차례대로
         *          1. 클래스 로더를 지정한다
         *              - 프록시가 어디에 생성될지를 지정한다.
         *          2. 들어갈 인터페이스들을 지정한다.
         *              - 얘는 배열로 넣어주어야 해서 , new Class[]{들어갈_인터페이스} 형태로 넣는다.
         *              왜냐면 여러개일수 있기 때문이다.
         *          3. 프록시가 사용할 로직이 무엇인지 넣어준다.
         *
         *          그리고 생성된 프록시는 아까 설정한 Ainterface의 타입을 가지기에
         *          Ainterface로 캐스팅이 가능하다.
         *
         *          그니까 프록시 클래스는 Proxy.newProxyInstance() 부분에서 만들어지는것이다.
         */
        Ainterface proxy = (Ainterface) Proxy.newProxyInstance(
                Ainterface.class.getClassLoader(),
                new Class[]{Ainterface.class},
                handler);
        //해당 프록시는 handler의 로직을 수행하게 된다.

        proxy.call();
        // 사용할땐 인터페이스의 메서드를 그냥 호출해주면 된다.
        // Ainterface의 call()을 호출한다.
        log.info("targetClass={}",target.getClass());
        log.info("proxyClass={}",proxy.getClass());
        /***
         * 결과 :
         * targetClass=class hello.proxy.jdkdynamic.code.AImpl
         * proxyClass=class com.sun.proxy.$Proxy9
         *
         * target은 Ainterface를 통해 만들어진 객체라는걸 알 수 있으며
         * 만들어진 proxy는 $Proxy9 라는것을 알 수 있다.
         *
         */
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
