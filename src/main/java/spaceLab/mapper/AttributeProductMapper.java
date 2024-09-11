package spaceLab.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import spaceLab.entity.AttributeProduct;
import spaceLab.model.AttributeProduct.AttributeProductResponse;
import spaceLab.entity.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {AttributeValueMapper.class})
public interface AttributeProductMapper {

    @Mapping(target = "productId", expression = "java(mapProductIds(attributeProduct.getProducts()))")
    @Mapping(source = "attributeValues", target = "attributeValueResponses")
    AttributeProductResponse toAttributeProductResponse(AttributeProduct attributeProduct);

    List<AttributeProductResponse> toAttributeProductResponse(List<AttributeProduct> attributeProducts);

    default List<Long> mapProductIds(List<Product> products) {
        return products != null ? products.stream().map(Product::getId).collect(Collectors.toList()) : new ArrayList<>();
    }
}
