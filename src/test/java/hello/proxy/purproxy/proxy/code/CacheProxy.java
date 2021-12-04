package hello.proxy.purproxy.proxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CacheProxy implements Subject{

    private Subject target; // 프록시가 호출하는 대상을 target이라 한다.
    private String cacheValue; // 실제 서버의 실행결과를 가지고있는 cacheValue

    public CacheProxy(Subject target){
        this.target = target;
    }

    @Override
    public String operation() {

        log.info("프록시 호출");

        if(cacheValue == null){ // 케시에 값이 없다면
            cacheValue = target.operation(); // operation()을 호출하고 cacheValue에 값을 넣어준다.
        }
        return cacheValue; // 값이 있다면 저장된 캐시를 반환하게끔 한다.
    }
}
