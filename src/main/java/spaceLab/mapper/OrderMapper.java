package spaceLab.mapper;


import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import spaceLab.entity.Order;
import spaceLab.model.order.response.OrderResponse;

import java.util.List;


@Mapper(componentModel = "spring", uses = {DeliveryMapper.class, OrderItemMapper.class})
public interface OrderMapper {

    OrderResponse orderToOrderResponse(Order order);

    List<OrderResponse> ordersToOrderResponses(List<Order> orders);

    default Page<OrderResponse> ordersToOrderResponses(Page<Order> orders) {
        return orders.map(this::orderToOrderResponse);
    }
}


