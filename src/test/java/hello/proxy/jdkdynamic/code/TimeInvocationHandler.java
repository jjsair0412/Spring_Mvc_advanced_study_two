package hello.proxy.jdkdynamic.code;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@Slf4j
public class TimeInvocationHandler implements InvocationHandler {

    private final Object target; // 프록시가 호출할 대상을 의존주입 받아준다. 데이터타입은 Object이기에 다 올수 있다.

    public TimeInvocationHandler(Object target){
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 프록시가 생성될때 공통적으로 수행할 로직 작성
        log.info("TimeProxy 실행");
        long startTime = System.currentTimeMillis();

        /***
         * 여기에 만약 Ainterface의 call() 메서드를 호출한다면 , method는 call이 될 것이다.
         */

        Object result = method.invoke(target, args);

        /***
         *  jdk 동적 프록시가 호출될 때 어떤 메서드가 실행될지도 넘어온다.
         *  따라서 method.invoke()를 사용해서 메서드를 실행한다.
         *  method.invoke()의 인자값으론 어떤 메서드가 오느냐 ( 위의 의존주입받은 호출대상 ) 을 넣어주고,
         *  그뒤엔 인자값 args 를 넣어준다.
         */

        long endTime = System.currentTimeMillis();

        long resultTime = endTime - startTime;
        log.info("TimeProxy 종료 resultTime={}",resultTime);
        return result;
    }
}
