package spaceLab.service.Imp;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import spaceLab.entity.Customer;
import spaceLab.entity.Invitation;
import spaceLab.repository.InvitationRepository;
import spaceLab.service.InvitationService;

@Service
@AllArgsConstructor
public class InvitationServiceImp implements InvitationService {

    private final InvitationRepository invitationRepository;

    @Override
    public Invitation validateInvitationCode(String inviteCode) {
        return invitationRepository.findByInviteCode(inviteCode);
    }

    @Override
    public Invitation findByInviterAndIsActive(String inviteCode) {
        return null;
    }

    @Override
    public Invitation findByInviterAndIsActive(Customer inviter, boolean isActive) {
        return invitationRepository.findByInviterAndIsActive(inviter, isActive);
    }

    @Override
    public Invitation saveInvitation(Invitation invitation) {
        return invitationRepository.save(invitation);
    }
}
