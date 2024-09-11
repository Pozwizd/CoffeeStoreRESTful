package spaceLab.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import spaceLab.entity.Order;
import spaceLab.model.order.request.OrderRequest;
import spaceLab.model.order.response.OrderResponse;

import java.util.List;

@Service
public interface OrderService {


    public Order getOrder(Long id);

    public OrderResponse getOrderResponse(Long id);

    public List<Order> getAllOrders();

    public void updateOrderFromOrderRequest(OrderRequest orderRequest);

    OrderResponse saveOrderFromOrderRequest(OrderRequest orderRequest);

    OrderResponse reorder(Long id);

    Page<OrderResponse> getOrdersByCustomerIdPage(Long customerId, Integer page, Integer size);

}
