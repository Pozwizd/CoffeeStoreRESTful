package spaceLab.service;


import spaceLab.model.authentication.ChangePasswordRequest;
import spaceLab.model.authentication.EmailRequest;

public interface PasswordResetTokenCustomerService {
    boolean validatePasswordResetToken(String token);
    void updatePassword(ChangePasswordRequest changePasswordRequest, String token);
    String createOrUpdatePasswordResetToken(EmailRequest emailRequest);
}
