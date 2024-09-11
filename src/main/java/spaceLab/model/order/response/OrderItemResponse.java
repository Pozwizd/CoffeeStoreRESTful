package spaceLab.model.order.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse implements Serializable {
    Long id;
    Long categoryId;
    Long productId;
    String productName;
    int quantity;
    List<OrderItemAttributeResponse> attributes;
}