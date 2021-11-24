package hello.proxy.app.v1;


import org.springframework.beans.factory.annotation.Autowired;

public class OrderControllerV1Impl implements OrderControllerV1{

    private final OrderServiceV1 orderService;

    @Autowired
    public OrderControllerV1Impl(OrderServiceV1 orderServiceV1){
        this.orderService = orderServiceV1;
    }

    @Override
    public String request(String itemId) {
        orderService.orderItem(itemId);
        return "ok";
    }

    @Override
    public String noLog() {
        return null;
    }
}
