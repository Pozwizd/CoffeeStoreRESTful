package spaceLab.service;

import org.springframework.stereotype.Service;
import spaceLab.entity.Customer;
import spaceLab.entity.Invitation;

@Service
public interface InvitationService {

    Invitation validateInvitationCode(String inviteCode);

    Invitation findByInviterAndIsActive(String inviteCode);

    Invitation findByInviterAndIsActive(Customer inviter, boolean isActive);

    Invitation saveInvitation(Invitation invitation);
}
