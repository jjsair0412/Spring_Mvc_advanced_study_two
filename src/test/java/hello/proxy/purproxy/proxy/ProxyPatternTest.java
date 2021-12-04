package hello.proxy.purproxy.proxy;

import hello.proxy.purproxy.proxy.code.CacheProxy;
import hello.proxy.purproxy.proxy.code.ProxyPatternClient;
import hello.proxy.purproxy.proxy.code.RealSubject;
import org.junit.jupiter.api.Test;
public class ProxyPatternTest {

    @Test
    void noProxyTest(){ // 프록시패턴 미적용
        RealSubject realSubject = new RealSubject();
        ProxyPatternClient client = new ProxyPatternClient(realSubject);
        client.execute();
        client.execute();
        client.execute();
        // 얘는 operation() 메서드가 1초동안 걸리기때문에 3초가 걸린다.
        // 근데 이 데이터가 만약 한번조회하면 변하지 않는 데이터라고 가정한다면,
        // 어딘가 보관한 뒤 한번 조회한 다음은 이미 조회한걸로 대체해서 보여주면 훨씬 빨리 조회가 가능할것이다.
        // 그래서 캐시를 사용하고, 캐시를 사용하기 위해 프록시를 클라이언트와 서버 중간에 끼워넣어보자.
    }

    @Test
    void cacheProxyTest(){ // 프록시패턴 적용
        RealSubject realSubject = new RealSubject();
        CacheProxy cacheProxy = new CacheProxy(realSubject); // 프록시
        ProxyPatternClient client = new ProxyPatternClient(cacheProxy);
        // 기존 프록시를 사용하지 않을때에는 클라이언트 ( ProxyPatternClient ) 에 realSubject라는 서버를 DI시켜주었지만,
        // 지금은 프록시가 끼워졌기때문에 CacheProxy를 DI시켜준다.
        client.execute();
        client.execute();
        client.execute();
        // 얘는 클라이언트 ( ProxyPatternClient ) 와 서버 ( RealSubject ) 중간에 캐시를 갖고잇는 프록시 ( CacheProxy ) 를 끼워줬다.
        // 캐시를 가지는 프록시는, operation()을 호출해서 나온 값을 저장해두었다가
        // 값을 가지고 있다면 반환하고 가지고있지 않다면 operation()을 호출하게끔 한다.
        // 따라서 캐시를 사용하지 않는 noProxyTest()보다 실행시간이 훨씬 빨라지게 된다.
    }
}
