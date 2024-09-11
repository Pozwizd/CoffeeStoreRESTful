package spaceLab.model.order.request;

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
public class OrderItemRequest implements Serializable {

    Long id;
    Long CategoryId;
    Long productId;
    String productName;
    int quantity;
    List<OrderItemAttributeRequest> orderItemAttributeRequests;
}