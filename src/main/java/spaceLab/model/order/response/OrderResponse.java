package spaceLab.model.order.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import spaceLab.entity.Order;
import spaceLab.model.delivery.DeliveryResponse;

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
public class OrderResponse implements Serializable {
    Long id;
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    LocalDateTime dateTimeOfCreate;
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    LocalDateTime dateTimeOfReady;

    DeliveryResponse deliveryDto;
    List<OrderItemResponse> orderItems;

    Order.Payment payment;
    Order.OrderStatus status;

    double totalAmount = 0;
}