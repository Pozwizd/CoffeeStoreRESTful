package spaceLab.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
    private String latitude;
    private String longitude;
    private String street;
    private String building;
    private String phoneNumber;

}
