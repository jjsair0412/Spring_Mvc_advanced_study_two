package hello.proxy.app.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping // 스프링은 @Controller 또는 @RequestMapping 어노테이션이 있어야 스프링 컨트롤러로 인식할 수 있다. 둘중에 하나만 있어도 컨트롤러로 인식한다.
@ResponseBody // 얘네들은 인터페이스에 등록시켜놓기만 하면, 해당 인터페이스의 구현체에서는 안써주어도 무관하다.
public interface OrderControllerV1 {

    @GetMapping("/v1/request")
    String request(@RequestParam("itemId") String itemId); // @RequestParam은 실제 Controller에는 생략해도 되지만, 인터페이스에서는 넣어주어야 한다.

    @GetMapping("/v1/no-log")
    String noLog();
}
