package spaceLab.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import spaceLab.entity.OrderItemAttribute;
import spaceLab.model.order.response.OrderItemAttributeResponse;

@Mapper(componentModel = "spring")
public interface OrderItemAttributeMapper {

    @Mapping(source = "attributeProduct.id", target = "productAttributeId")
    @Mapping(source = "attributeValue.id", target = "attributeValueId")
    OrderItemAttributeResponse orderItemAttributeToOrderItemAttributeResponse(OrderItemAttribute orderItemAttribute);
}
