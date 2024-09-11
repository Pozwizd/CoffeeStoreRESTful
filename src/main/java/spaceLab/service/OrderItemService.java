package spaceLab.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import spaceLab.entity.Order;
import spaceLab.entity.OrderItem;
import spaceLab.model.order.request.OrderItemRequest;

import java.util.List;

@Service
public interface OrderItemService {

    OrderItem saveOrderItem(OrderItem orderItem);

    void deleteOrderItem(OrderItem orderItem);

    List<OrderItem> getAllOrderItems();

    OrderItem getOrderItem(Long id);

    Page<OrderItem> findAllOrderItems(int page, int pageSize);

    long countOrderItems();

    OrderItem updateOrderItem(OrderItem orderItem);

    void deleteOrderItem(Long id);

    List<OrderItem> getOrderItemsByOrderId(Long orderId);


    OrderItem saveOrderItem(OrderItemRequest orderItemDto, Order savedOrder);

    boolean deleteAll(List<OrderItem> itemsToRemove);
}
