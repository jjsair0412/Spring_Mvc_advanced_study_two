package hello.proxy.cglib.code;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

@Slf4j
public class TimeMethodInterceptor implements MethodInterceptor {
    // Spring framework 에 잇는 MethodInterceptor를 갖고와야 한다.

    private final Object target; // 프록시가 호출할 실제 대상

    public TimeMethodInterceptor(Object target) {
        this.target = target;
    }


    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        // JDK 동적 프록시에서와 동일하게 여기에 공통로직을 작성한다.
        log.info("TimeProxy 실행");
        long startTime = System.currentTimeMillis();

        Object result = methodProxy.invoke(target, args);
        // CGLIB에서는 MethodProxy라는애를 통해서 invoke하는것을 더 권장한다.

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("TimeProxy 종료 resultTime={}",resultTime);
        return result;
    }
}
