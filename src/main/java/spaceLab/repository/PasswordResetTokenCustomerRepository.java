package spaceLab.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import spaceLab.entity.PasswordResetTokenCustomer;

import java.util.Optional;

public interface PasswordResetTokenCustomerRepository extends JpaRepository<PasswordResetTokenCustomer, Long> {
    Optional<PasswordResetTokenCustomer> findByToken(String token);
}
