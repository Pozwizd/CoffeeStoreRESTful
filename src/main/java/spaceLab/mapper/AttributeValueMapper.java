package spaceLab.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import spaceLab.entity.AttributeValue;
import spaceLab.model.attributeValue.AttributeValueResponse;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AttributeValueMapper {

    @Mapping(target = "priceWithDiscount", expression = "java(attributeValue.getPriceWithDiscount() != null ? attributeValue.getPriceWithDiscount() : 0.0)")
    AttributeValueResponse attributeValueToAttributeValueResponse(AttributeValue attributeValue);

    List<AttributeValueResponse> toAttributeValueResponseList(List<AttributeValue> attributeValues);
}
