package spaceLab.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import spaceLab.entity.Product;
import spaceLab.model.product.ProductResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring", uses = {AttributeProductMapper.class})
public interface ProductMapper {

    @Named("mapToId")
    default Long mapToId(Product product) {
        return product != null ? product.getId() : null;
    }

    @Named("mapToIdList")
    default List<Long> mapToIdList(List<Product> products) {
        return products != null ? products.stream().map(this::mapToId).collect(Collectors.toList()) : new ArrayList<>();
    }

    @Mapping(source = "category.name", target = "category")
    @Mapping(source = "attributeProducts", target = "attributeProducts") // Маппинг атрибутов с использованием AttributeProductMapper
    ProductResponse productToProductResponse(Product product);

    List<ProductResponse> productsToProductResponses(List<Product> products);
}
