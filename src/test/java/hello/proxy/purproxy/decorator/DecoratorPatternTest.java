package hello.proxy.purproxy.decorator;

import hello.proxy.purproxy.decorator.code.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class DecoratorPatternTest {

    @Test
    void noDecorator(){
        // 데코레이터 패턴 적용 전
        RealComponent realComponent = new RealComponent();
        DecoratorPatternClient client = new DecoratorPatternClient(realComponent);
        client.excuete();
    }

    @Test
    void decorator1(){ // decorator 적용 후 . data값 꾸며서 응답
        Component realComponent = new RealComponent();
        Component messageDecorator = new MessageDecorator(realComponent);
        DecoratorPatternClient client = new DecoratorPatternClient(messageDecorator);

        /**
         * 보면 , 프록시패턴과 적용방식이 정말 흡사하다.
         * 근데 데코레이터 패턴은 결과값에 부가기능을 추가해서 다른 응답값을 리턴해주거나
         * 새로운기능을 추가한다는 의도에 차이가 있다.!!!
         */

        client.excuete();
        /**
         * 실행 결과 :
         * MessageDecorator 꾸미기 적용 전 =data
         * MessageDecorator 꾸미기 적용 후 =****data*****
         * result =****data*****
         */
    }


    @Test
    void decorator2(){
        /**
         * 데코레이터 패턴에서의 체인의 의미는 , 핵심기능에 부가기능을 계속 추가한다 에 있다.
         * 프록시패턴에서의 체인의 의미는 , 접근제어를 여러번 확인한다 에 있다.
         */

        Component realComponent = new RealComponent();
        Component messageDecorator = new MessageDecorator(realComponent);
        Component timeDecorator = new TimeDecorator(messageDecorator);
        DecoratorPatternClient client = new DecoratorPatternClient(timeDecorator);
        /**
         *  클라이언트 -> timeDecorator -> messageDecorator -> realComponent
         *  이 순서대로 의존한다.
         *
         *  당연히 데코레이터들은 모두 같은 interface를 구현한 구현ㅎ체이다.
         */
        client.excuete();

    }
}
