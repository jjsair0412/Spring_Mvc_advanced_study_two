package hello.proxy.app.v2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping
@ResponseBody
@Slf4j
public class OrderControllerV2 {

    private final OrderServiceV2 orderService;

    @Autowired
    public OrderControllerV2(OrderServiceV2 orderServiceV2){
        this.orderService = orderServiceV2;
    }

    @GetMapping("v2/request")
    public String request(@RequestParam("itemId") String itemId) {
        orderService.orderItem(itemId);
        return "ok";
    }

    @GetMapping("/v2/no-log")
    public String noLog() {
        return "ok";
    }
}
