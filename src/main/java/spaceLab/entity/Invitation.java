package spaceLab.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Invitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String inviteCode;  // Постоянный код приглашения

    @ManyToOne
    @JoinColumn(name = "inviter_id")
    private Customer inviter;   // Кто пригласил

    private boolean isActive;   // Активна ли ссылка

    @OneToMany(mappedBy = "invitation", cascade = CascadeType.ALL)
    private List<Customer> invitedCustomers = new ArrayList<>();  // Кто зарегистрировался по этому приглашению
}
