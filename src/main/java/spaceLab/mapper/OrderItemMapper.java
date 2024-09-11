package spaceLab.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import spaceLab.entity.OrderItem;
import spaceLab.model.order.response.OrderItemResponse;

@Mapper(componentModel = "spring", uses = {OrderItemAttributeMapper.class})
public interface OrderItemMapper {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.category.id", target = "categoryId")
    @Mapping(source = "product.name", target = "productName")
    OrderItemResponse orderItemToOrderItemResponse(OrderItem orderItem);
}
