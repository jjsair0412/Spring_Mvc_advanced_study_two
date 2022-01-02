package hello.proxy.cglib;
import hello.proxy.cglib.code.TimeMethodInterceptor;
import hello.proxy.common.service.ConcreteService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;

@Slf4j
public class CglibTest {
    @Test
    void cglib(){
        ConcreteService target = new ConcreteService();

        // Enhancer라는 애를 통해서 CGLIB를 사용, 프록시를 생성하게 된다.
        Enhancer enhancer = new Enhancer();
        // Enhancer의 setSuperclass()를 통해 어떤 구체클래스를 상속받을지 지정한다.
        // 여기선 ConcreteService를 상속받아 프록시를 생성한다.
        enhancer.setSuperclass(ConcreteService.class);
        // 그후 setCallback()에 MethodInterceptor의 구체클래스를 넣어준다.
        enhancer.setCallback(new TimeMethodInterceptor(target));
        // create() 메서드를 통해 프록시를 찍어낸다.
        // ConcreteService 데이터타입으로 캐스팅이가능한데 , 그 이유는
        // setSuperClass()에서 상속받은 프록시의 부모타입이 바로 ConcreteService이기 때문이다.
        ConcreteService proxy = (ConcreteService) enhancer.create();

        log.info("targetClass={}",target.getClass());
        log.info("proxyClass={}",proxy.getClass());
        /***
         * 결과 :
         * targetClass=class hello.proxy.common.service.ConcreteService
         * proxyClass=class hello.proxy.common.service.ConcreteService$$EnhancerByCGLIB$$25d6b0e3
         *
         * targetClass는 당연하게도 클래스타입이 ConcreteService이다.
         * 그러나 CGLIB를 통해 만든 proxy는 데이터타입에 CGLIB가 쓰여있따 ㅋㅋ
         */
        proxy.call();
    }
}
