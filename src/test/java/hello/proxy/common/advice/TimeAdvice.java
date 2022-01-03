package hello.proxy.common.advice;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

@Slf4j
public class TimeAdvice implements MethodInterceptor { // MethodInterceptor를 구현함
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        /***
         *  target정보가 있지 않고 , target 클래스 정보는 MethodInvocation에서 다 알고있기 때문이다.
         *  그런 이유는 프록시 팩토리를 생성할 때 타겟정보를 넘겨주기 때문이다.
         *
         */

        log.info("TimeProxy 실행");
        long startTime = System.currentTimeMillis();

//        Object result = method.invoke(target, args);
        Object result = invocation.proceed();

        /***
         * 기존 invoke를 사용하는것이 아니라 , invocation의 proceed() 메서드를 사용한다.
         *
         * 얘는 자동으로 target을 찾아주고 args를 대입해주고 , getClass나 getMethod, getArgs등
         * 알아서 인자값이나 메서드명등을 찾아줄 수 있다.
         */


        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("TimeProxy 종료 resultTime={}",resultTime);
        return result;
    }
}
