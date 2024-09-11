package spaceLab.service;

import org.springframework.stereotype.Service;
import spaceLab.entity.OrderItem;
import spaceLab.entity.OrderItemAttribute;
import spaceLab.model.order.request.OrderItemAttributeRequest;

@Service
public interface OrderItemAttributeService {


    OrderItemAttribute saveOrderItemAttribute(OrderItemAttribute orderItemAttribute);

    OrderItemAttribute updateOrderItemAttribute(OrderItemAttribute orderItemAttribute);

    void deleteOrderItemAttribute(Long id);

    OrderItemAttribute getOrderItemAttribute(Long id);

    Iterable<OrderItemAttribute> getAllOrderItemAttributes();

    Iterable<OrderItemAttribute> getAllOrderItemAttributesByOrderItemId(Long orderItemId);

    void deleteAllOrderItemAttributesByOrderItemId(Long orderItemId);

    OrderItemAttribute saveOrderItemAttribute(OrderItemAttributeRequest orderItemAttributeRequest, OrderItem orderItem);
}
