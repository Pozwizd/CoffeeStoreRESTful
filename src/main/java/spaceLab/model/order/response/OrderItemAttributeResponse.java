package spaceLab.model.order.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemAttributeResponse implements Serializable {
    Long id;
    Long orderItemId;
    Long productAttributeId;
    Long attributeValueId;
}