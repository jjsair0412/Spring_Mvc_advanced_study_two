package hello.proxy.app.v2;

import org.springframework.beans.factory.annotation.Autowired;

public class OrderServiceV2 {
    private final OrderRepositoryV2 orderRepository;

    @Autowired
    public OrderServiceV2(OrderRepositoryV2 orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void orderItem(String itemId) {
        orderRepository.save(itemId);
    }
}
