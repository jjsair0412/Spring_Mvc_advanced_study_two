package hello.proxy.app.v3;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@ResponseBody
public class OrderControllerV3 {

    private final OrderServiceV3 orderService;

    @Autowired
    public OrderControllerV3(OrderServiceV3 orderServiceV3){
        this.orderService = orderServiceV3;
    }

    @GetMapping("v3/request")
    public String request(@RequestParam("itemId") String itemId) {
        orderService.orderItem(itemId);
        return "ok";
    }

    @GetMapping("/v3/no-log")
    public String noLog() {
        return "ok";
    }
}
