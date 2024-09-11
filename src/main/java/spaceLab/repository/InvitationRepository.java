package spaceLab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spaceLab.entity.Customer;
import spaceLab.entity.Invitation;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    Invitation findByInviteCode(String inviteCode);

    Invitation findByInviterAndIsActive(Customer inviter, boolean isActive);
}
