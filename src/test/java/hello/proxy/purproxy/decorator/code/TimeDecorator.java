package hello.proxy.purproxy.decorator.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeDecorator implements Component{

    private Component component;

    public TimeDecorator(Component component){
        this.component =component;
    }

    @Override
    public String operation() { // 실행시간을 측정해서 로그를 찍는다.

        log.info("TimeDecorator 실행");
        long startTime = System.currentTimeMillis();

        String result = component.operation();

        long endTime = System.currentTimeMillis();

        long resultTime = endTime - startTime;
        log.info("TimeDecorator 종료={}",resultTime);

        return result;
    }
}
