package spaceLab.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    private String email;

    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private PasswordResetToken passwordResetToken;

    @Enumerated(EnumType.STRING)
    private Role role;


    @Getter
    public enum Role {
        ADMIN("Администратор"),
        MANAGER("Менеджер");

        Role(String roleName) {
            this.roleName = roleName;
        }
        private final String roleName;

    }
}

