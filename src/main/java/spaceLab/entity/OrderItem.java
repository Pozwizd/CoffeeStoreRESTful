package spaceLab.entity;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue
    private Long id;

    private int quantity;

    @ManyToOne
    private Product product;

    @ManyToOne
    @ToString.Exclude
    private Order order;

    @OneToMany(mappedBy = "orderItem", cascade = CascadeType.ALL)
    private List<OrderItemAttribute> orderItemAttributes = new ArrayList<>();

    private double totalAmount;

}
