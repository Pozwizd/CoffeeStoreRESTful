package spaceLab.model.order.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import spaceLab.entity.OrderItemAttribute;

import java.io.Serializable;


/**
 * DTO for {@link OrderItemAttribute}
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemAttributeRequest implements Serializable {
    Long id;
    Long orderItemId;
    Long productAttributeId;
    Long attributeValueId;
}