package spaceLab.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import spaceLab.entity.OrderItem;
import spaceLab.entity.OrderItemAttribute;
import spaceLab.model.order.response.OrderItemAttributeResponse;
import spaceLab.model.order.response.OrderItemResponse;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {OrderItemAttributeMapper.class})
public interface OrderItemMapper {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.category.id", target = "categoryId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "orderItemAttributes", target = "attributes",  qualifiedByName = "orderItemAttributesToOrderItemAttributeResponses")
    OrderItemResponse orderItemToOrderItemResponse(OrderItem orderItem);

    List<OrderItemResponse> orderItemsToOrderItemResponses(List<OrderItem> orderItems);


    @Named("orderItemAttributesToOrderItemAttributeResponses")
    default List<OrderItemAttributeResponse> orderItemAttributesToOrderItemAttributeResponses(List<OrderItemAttribute> orderItemAttributes) {
        return orderItemAttributes.stream()
                .map(orderItemAttribute -> new OrderItemAttributeMapperImpl().orderItemAttributeToOrderItemAttributeResponse(orderItemAttribute))
                .collect(Collectors.toList());
    }
}