package hello.proxy.purproxy.proxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CacheProxy implements Subject{

    private Subject target;
    private String chacheValue;

    public CacheProxy(Subject target){
        this.target = target;
    }

    @Override
    public String operation() {

        log.info("프록시 호출");

        if(chacheValue == null){
            chacheValue = target.operation();
        }
        return chacheValue;
    }
}
