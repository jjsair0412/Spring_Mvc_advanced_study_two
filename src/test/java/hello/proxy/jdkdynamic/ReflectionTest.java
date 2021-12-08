package hello.proxy.jdkdynamic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class ReflectionTest {

    @Test
    void reflection0(){
        Hello target = new Hello();

        /***
         * 공통로직1과 공통로직 2는 호출 메서드만 다르고 코드 흐름이 완전히 같다.
         * 여기서 공통로직 1과 공통로직 2를 하나의 메서드로 뽑아서 해결할 수 있나 ?
         * 쉽지않다. 왜냐면 호출하는 메서드가 다르기 때문이다.
         *
         * 이럴때 리플렉션 기술을 사용한다.
         *
         *  리플렉션은 클래스나 메서드의 메타정보를 사용해서 동적으로 호출하는 메서드를 변경할 수 있다.
         */
        // 공통 로직 1 시작
        log.info("start");
        String result1 = target.callA();
        log.info("result1={}",result1);
        // 공통 로직 1 종료

        // 공통 로직 2 시작
        log.info("start");
        String result2 = target.callB();
        log.info("result2={}",result2);
        // 공통 로직 2 종료
    }

    @Test
    void reflection1() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 클래스 정보 획득중 ..
        Class classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");

        Hello target = new Hello();
        // callA의 메서드 정보
        Method methodCallA = classHello.getMethod("callA"); // 메서드정보를 문자로 받는다.
        Object result1 = methodCallA.invoke(target);
        // methodCallA는 "callA"라는 문자가 들어있다.
        // invoke를 통해 target ( Hello.class ) 내부의 "callA" 이름을 가진 메서드를 실행해 결과값을 꺼내오는것이다.
        log.info("result1={}",result1);

        Method methodCallB = classHello.getMethod("callB"); // 메서드정보를 문자로 받는다.
        Object result2 = methodCallB.invoke(target);
        log.info("result2={}",result2);

        // 여기서 중요한점은 , 메서드를 직접 호출하는 부분
        // String result1 = target.callA();
        // String result2 = target.callB();
        // 이부분을 추상화시켰다는 점이 중요한거다.
    }

    @Test
    void reflection2() throws Exception{
        // 클래스 정보
        Class classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");

        Hello target = new Hello();

        // callA의 메서드 정보
        Method methodCallA = classHello.getMethod("callA");
        dynamicCall(methodCallA, target);

        // callB의 메서드 정보
        Method methodCallB = classHello.getMethod("callB");
        dynamicCall(methodCallB, target);
    }

    private void dynamicCall(Method method,Object target) throws Exception{
        // 메서드정보와 클래스정보를 메타정보로 뽑아왔기 때문에, 동적으로 메서드를 조작할 수 있다.
        log.info("start");
        Object result = method.invoke(target);
        log.info("result={}",result);
    }

    @Slf4j
    static class Hello{
        public String callA(){
            log.info("callA");
            return "A";
        }

        public String callB(){
            log.info("callB");
            return "B";
        }
    }
}
