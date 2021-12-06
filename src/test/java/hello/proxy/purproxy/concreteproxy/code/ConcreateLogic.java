package hello.proxy.purproxy.concreteproxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
// 인터페이스가 없는 구체클래스도 프록시를 도입할 수 있다.
public class ConcreateLogic {

    public String operation(){
        log.info("ConcreateLogic 실행");
        return "data";
    }
}
