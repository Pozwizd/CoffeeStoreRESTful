package spaceLab.model.attributeValue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import spaceLab.entity.AttributeValue;

import java.io.Serializable;


/**
 * Response for {@link AttributeValue}
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttributeValueResponse implements Serializable {
    Long id;
    String name;
    String description;
    Double price;
    Double priceWithDiscount;
}