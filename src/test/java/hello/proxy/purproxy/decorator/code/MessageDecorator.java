package hello.proxy.purproxy.decorator.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageDecorator implements Component{

    private Component component;

    public MessageDecorator(Component component){
        this.component =component;
    }

    @Override
    public String operation() {
        log.info("MessageDecorator 실행");

        String result = component.operation(); // 실제객체 호출, data 반환
        String decoResult = "****" + result + "*****"; // 실제 객체가 반환한 값에 추가로 꾸며서 반환

        log.info("MessageDecorator 꾸미기 적용 전 ={}",result);
        log.info("MessageDecorator 꾸미기 적용 후 ={}",decoResult);
        return decoResult;
    }
}
