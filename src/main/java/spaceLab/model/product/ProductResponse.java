package spaceLab.model.product;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import spaceLab.entity.Product;
import spaceLab.model.AttributeProduct.AttributeProductResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Response for {@link Product}
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse implements Serializable {

    Long id;
    String name;
    String description;
    Double price;
    Double priceWithDiscount;
    Integer quantity;
    String status;
    String category;
    List<AttributeProductResponse> attributeProducts;

}