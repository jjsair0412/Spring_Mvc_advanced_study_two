package hello.proxy.proxyfactory;

import hello.proxy.common.advice.TimeAdvice;
import hello.proxy.common.service.ConcreteService;
import hello.proxy.common.service.ServiceImpl;
import hello.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ProxyFactoryTest {

    @Test
    @DisplayName("인터페이스가 있으면 JDK 동적 프록시 사용")
    void interfaceProxy(){
        ServiceInterface target = new ServiceImpl();

        // 프록시 팩토리를 생성할 때 , target을 넣어주기에 Advice에서 타겟정보가 없어도 되는것이다.
        // 이 target에 인터페이스가 있다면 JDK 동적 프록시를 적용하고 ,
        // 이 target에 구체클래스만 있다면 CGLIB를 적용한다.
        ProxyFactory proxyFactory = new ProxyFactory(target);

        // 생성한프록시팩토리 객체에 Advice를 넣어준다.
        proxyFactory.addAdvice(new TimeAdvice());

        // 프록시 팩토리의 getProxy()를 통해서 프록시를 꺼내온다.
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}",proxy.getClass());

        /***
         * 결과 :
         * targetClass=class hello.proxy.common.service.ServiceImpl
         * proxyClass=class com.sun.proxy.$Proxy10
         *
         * prxoy는 인터페이스가 존재하기때문에 JDK 동적 프록시가 적용된것을 볼 수 있다.
         */

        proxy.save();


        /***
         * 프록시 팩토리를 사용한다면 , 만들어진 프록시가 AoppProxy가 맞는지,
         * JDK동적프록시를통해 만들어진 프록시인지,
         * CGLIB인지를 판단하는 메서드를 제공한다.
         *
         * 아래처럼 사용하면 된다.
         */
        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isTrue();
        assertThat(AopUtils.isCglibProxy(proxy)).isFalse();
    }


    @Test
    @DisplayName("구체 클래스만 있는 경우 CGLIB 사용")
    void CGLIB_Proxy_Test(){
        ConcreteService target = new ConcreteService();

        ProxyFactory proxyFactory = new ProxyFactory(target);

        proxyFactory.addAdvice(new TimeAdvice());

        ConcreteService proxy = (ConcreteService) proxyFactory.getProxy();

        log.info("targetClass={}",target.getClass());
        log.info("proxyClass={}",proxy.getClass());

        /***
         * 결과 :
         * targetClass=class hello.proxy.common.service.ConcreteService
         * proxyClass=class hello.proxy.common.service.ConcreteService$$EnhancerBySpringCGLIB$$9c7ecb6
         *
         * 구체클래스만 존재하기에 CGLIB가 적용된 모습을 볼 수 있다.
         */

        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();

        proxy.call();

    }

    @Test
    @DisplayName("ProxyTargetClass 옵션을 사용하면 인터페이스가 있다 하더라도 CGLIB를 사용한다.")
    void ProxyTargetClass(){
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);

        proxyFactory.setProxyTargetClass(true); // CGLIB를 기반으로 만들게끔 옵션 변경

        proxyFactory.addAdvice(new TimeAdvice());

        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}",proxy.getClass());

        /***
         * 결과 :
         * targetClass=class hello.proxy.common.service.ServiceImpl
         * proxyClass=class hello.proxy.common.service.ServiceImpl$$EnhancerBySpringCGLIB$$37b71a89
         *
         * 인터페이스가 있지만 , 동적으로 CGLIB를 사용해서 프록시를 만들게끔 했다.
         */

        proxy.save();

        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
        assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
    }


}
