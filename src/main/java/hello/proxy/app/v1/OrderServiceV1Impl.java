package hello.proxy.app.v1;

import org.springframework.beans.factory.annotation.Autowired;

public class OrderServiceV1Impl implements OrderServiceV1{

    private final OrderRepositoryV1 orderRepository;

    @Autowired
    public OrderServiceV1Impl(OrderRepositoryV1 orderRepositoryV1){
        this.orderRepository =orderRepositoryV1;
    }
    @Override
    public void orderItem(String itemId) {
        orderRepository.save(itemId);
    }
}
