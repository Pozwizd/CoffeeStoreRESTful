package spaceLab.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import spaceLab.entity.Order;
import spaceLab.entity.OrderItem;
import spaceLab.model.order.response.OrderItemResponse;
import spaceLab.model.order.response.OrderResponse;

import java.util.List;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring", uses = {DeliveryMapper.class, OrderItemMapper.class})
public interface OrderMapper {

    @Mapping(source = "orderItems", target = "orderItems", qualifiedByName = "orderItemsToOrderItemsDto")
    OrderResponse orderToOrderResponse(Order order);

    List<OrderResponse> ordersToOrderResponses(List<Order> orders);

    default Page<OrderResponse> ordersToOrderResponses(Page<Order> orders) {
        return orders.map(this::orderToOrderResponse);
    }

    @Named("orderItemsToOrderItemsDto")
    default List<OrderItemResponse> orderItemsToOrderItemsDto(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(orderItem -> new OrderItemMapperImpl().orderItemToOrderItemResponse(orderItem))
                .collect(Collectors.toList());
    }


}
