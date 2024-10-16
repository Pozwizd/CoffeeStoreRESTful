package spaceLab.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String email;
    private String password;
    @Column(name = "date_of_birth", columnDefinition="DATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private double bonusPoints = 0;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private List<Order> orders = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private Language language;
    @ManyToOne
    @JoinColumn(name = "invitation_id")
    private Invitation invitation;
    @Column(name = "registration_date", columnDefinition="DATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate registrationDate;
    @Enumerated(EnumType.STRING)
    private CustomerStatus status;
    @OneToOne(mappedBy = "customer")
    private PasswordResetTokenCustomer passwordResetTokenCustomer;
    private boolean deleted = false;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
