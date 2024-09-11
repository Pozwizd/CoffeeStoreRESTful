package spaceLab.model.order.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import spaceLab.entity.Order;
import spaceLab.model.delivery.DeliveryRequest;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


/**
 * Response for {@link Order}
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest implements Serializable {
    Long id;
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    LocalDateTime dateTimeOfCreate;
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    LocalDateTime dateTimeOfReady;
    DeliveryRequest deliveryRequest;
    List<OrderItemRequest> orderItemRequests;
    Order.Payment payment;
    Order.OrderStatus status;
    double totalAmount = 0;
}