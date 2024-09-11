package spaceLab.model.delivery;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import spaceLab.entity.Delivery;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * response model for {@link Delivery}
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryResponse implements Serializable {
    Long id;
    String name;
    String phoneNumber;
    Long cityId;
    String street;
    String building;
    String subDoor;
    String apartment;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate deliveryDate;
    @DateTimeFormat(pattern = "HH:mm")
    LocalTime deliveryTime;
    Double changeAmount;
    Delivery.DeliveryStatus status;
}