package hello.proxy.advisor;
import hello.proxy.common.advice.TimeAdvice;
import hello.proxy.common.service.ServiceImpl;
import hello.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

import java.awt.*;
import java.lang.reflect.Method;
@Slf4j
public class AdvisorTest {

    @Test
    void advisorTest1(){
        ServiceInterface target = new ServiceImpl();

        ProxyFactory proxyFactory = new ProxyFactory(target);

        /***
         * advisor를 만들때 DefaultPointcutAdvisor를 사용한다.
         *
         *
         * 인자값으로는 순서대로 Pointcut과 어드바이저가 들어간다.
         *
         * Pointcut.TRUE는 항상 참이되는 포인트컷이고 , TimeAdvice()는 아까 만들어놨던 어드바이스이다.
         *
         * 만든 어드바이저를 아까 만들어준 프록시팩토리에 addAvisor()를 통해 넣어준다.
         */
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(Pointcut.TRUE, new TimeAdvice());
        proxyFactory.addAdvisor(advisor);

        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.save();
        proxy.find();
    }

    @Test
    @DisplayName("직접 만든 포인트컷")
    void advisorTest2(){
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        // 직접 만든 MyPointcut을 넣어준다.
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(new MyPointcut(), new TimeAdvice());
        proxyFactory.addAdvisor(advisor);
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        // save일경우에만 어드바이스 실행
        proxy.save();
        proxy.find();
        /***
         * 결과 :
         *
         * 포인트컷 호출 method = save targetClass=class hello.proxy.common.service.ServiceImpl
         * 포인트컷 결과 result =true
         * TimeProxy 실행
         * save 호출
         * TimeProxy 종료 resultTime=0
         * 포인트컷 호출 method = find targetClass=class hello.proxy.common.service.ServiceImpl
         * 포인트컷 결과 result =false
         * find 호출
         *
         * 실행 순서 :
         * 1. 클라이언트가 프록시 호출
         * 2. 프록시는 포인트컷으로 어드바이스를 적용할지 말지 정함
         * 3. 어드바이스를 적용한다면 , 적용해서 결과 반환
         * 3. 적용하지 않는다면 , 어드바이스 호출안하고 바로 통과
         */
    }

    @Test
    @DisplayName("스프링이 제공하는 포인트컷")
    void advisorTest3() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);


        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();

        pointcut.setMappedName("save");

        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, new TimeAdvice());
        proxyFactory.addAdvisor(advisor);
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.save();
        proxy.find();
    }

    // 요구사항으로는 , save라는 메서드가 넘어올때만 어드바이스를 적용하도록 하는것이다.
    static class MyPointcut implements Pointcut{ // 직접 구현한 포인트컷
        @Override
        public ClassFilter getClassFilter() {
            return ClassFilter.TRUE; // 클래스필터는 항상 TRUE를 반환하게끔 했다.
        }

        @Override
        public MethodMatcher getMethodMatcher() {
            return new MyMethodMatcher();
        }
    }

    static class MyMethodMatcher implements MethodMatcher{

        private String matchName = "save";



        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            // Class정보랑 Method정보가 넘어오게 되는데, 이 정보로 어드바이스를 적용할지 말지를 판단한다.
            // 여기선 메서드이름이 save일 경우에만 어드바이스를 적용하도록 구현해놨다.
            boolean result =  method.getName().equals(matchName);
            log.info("포인트컷 호출 method = {} targetClass={}",method.getName(),targetClass);
            log.info("포인트컷 결과 result ={}",result);
            return result;
        }

        @Override
        public boolean isRuntime() {
            // isRuntime()값이 참이면 , matches(Method method, Class<?> targetClass, Object... args) 가 대신 호출된다.
            // false면 matches(Method method, Class<?> targetClass)을 호출한다.
            // 그런데 인자값이 넘어오는경우는 캐싱하지 않는다. 인자값이 동적으로 들어오기때문이다. ( 참고만 하자 )
            return false; // 무시
        }

        @Override
        public boolean matches(Method method, Class<?> targetClass, Object... args) {
            return false; // 무시
        }
    }

}
