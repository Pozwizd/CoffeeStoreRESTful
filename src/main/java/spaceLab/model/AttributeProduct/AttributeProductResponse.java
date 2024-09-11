package spaceLab.model.AttributeProduct;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import spaceLab.entity.AttributeProduct;
import spaceLab.model.attributeValue.AttributeValueResponse;

import java.io.Serializable;
import java.util.List;


/**
 * Response for {@link AttributeProduct}
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttributeProductResponse implements Serializable {

    Long id;
    String name;
    String type;
    List<Long> productId;
    Boolean status;
    List<AttributeValueResponse> attributeValueResponses;
}