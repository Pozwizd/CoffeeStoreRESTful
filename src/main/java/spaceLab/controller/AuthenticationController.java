package spaceLab.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import spaceLab.entity.Customer;
import spaceLab.entity.Invitation;
import spaceLab.model.authentication.*;
import spaceLab.model.customer.CustomerRequest;
import spaceLab.repository.InvitationRepository;
import spaceLab.service.*;

import java.util.UUID;

/**
 * AuthenticationController
 * - authenticationService: AuthenticationService
 * - passwordResetTokenCustomerService: PasswordResetTokenCustomerService
 * - mailService: MailService
 * --
 * + registerCustomer(@Valid @RequestBody CustomerRequest customerRequest): ResponseEntity<AuthenticationResponse>
 * + authenticateCustomer(AuthenticationRequest authenticationRequest): ResponseEntity<AuthenticationResponse>
 * + refreshToken(RefreshToken refreshToken): ResponseEntity<AuthenticationResponse>
 * + resetPassword(PasswordResetRequest passwordResetRequest, HttpServletRequest request)
 * + sendResetPasswordEmail(PasswordResetRequest passwordResetRequest, HttpServletRequest request)
 * + validateResetToken(String token)
 * + forgotPassword(HttpServletRequest httpRequest,@Valid @RequestBody EmailRequest emailRequest)
 * + changePassword(PasswordResetRequest passwordResetRequest)
 */

@Tag(name = "Authentication")
@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@SecurityRequirement(name = "empty")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final PasswordResetTokenCustomerService passwordResetTokenCustomerService;
    private final MailService mailService;
    private final CustomerService customerService;
    private final InvitationService invitationService;



    @Operation(summary = "Generate invite link", description = "Generates a permanent invite link for the customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema())}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {@Content(mediaType = "application/json", schema = @Schema())}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(mediaType = "application/json", schema = @Schema())})
    })
    @GetMapping("/generateInviteLink")
    public ResponseEntity<String> generateInviteLink(HttpServletRequest request) {
        // Получаем текущего аутентифицированного пользователя
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUsername();

        // Ищем пользователя по email
        Customer inviter = customerService.getCustomerByEmail(email);

        // Проверяем, есть ли уже активная ссылка для этого пользователя
        Invitation invitation = invitationService.findByInviterAndIsActive(inviter, true);
        if (invitation == null) {
            // Если нет, создаем новую
            invitation = new Invitation();
            invitation.setInviteCode(UUID.randomUUID().toString());  // Генерация кода приглашения
            invitation.setInviter(inviter);
            invitation.setActive(true);
            invitationService.saveInvitation(invitation);
        }

        // Формируем постоянную ссылку
        String inviteLink = request.getRequestURL().toString().replace("/generateInviteLink", "/register?inviteCode=") + invitation.getInviteCode();

        // Возвращаем ссылку
        return new ResponseEntity<>(inviteLink, HttpStatus.OK);
    }




    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
        // Проверяем код приглашения
        Invitation invitation = invitationService.validateInvitationCode(customerRequest.getToken());
        if (invitation == null || !invitation.isActive()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Неверный или неактивный код
        }

        // Выполняем регистрацию
        AuthenticationResponse response = authenticationService.register(customerRequest);

        // Привязываем нового пользователя к приглашению
        invitation.getInvitedCustomers().add(customerService.getCustomerByEmail(customerRequest.getEmail()));
        invitationService.saveInvitation(invitation);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @Operation(summary = "Customer authentication",
            description = "Authenticate Customer"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = AuthenticationResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Wrong email or password",content = {@Content(mediaType = "application/json",schema = @Schema())}),
            @ApiResponse(responseCode = "400", description = "Failed validation",content = {@Content(mediaType = "application/json",schema = @Schema())})})
    @SecurityRequirement(name = "")
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody AuthenticationRequest authenticationRequest){
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }

    @Operation(summary = "Refresh access token",description = "Get new access token by refresh token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = AuthenticationResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Not found customer by refresh token",content = {@Content(mediaType = "application/json",schema = @Schema())}),
            @ApiResponse(responseCode = "401", description = "Wrong refresh token",content = {@Content(mediaType = "application/json",schema = @Schema())})})
    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshToken refreshToken){
        if(refreshToken.getRefreshToken().equals("")){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        AuthenticationResponse authenticationResponse = authenticationService.refreshToken(refreshToken);
        if (authenticationResponse != null) {
            return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Sending email to customer to change password",description = "Request email and send password reset token to this email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = PasswordResetTokenResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Customer with such email not found",content = {@Content(mediaType = "application/json",schema = @Schema())}),
            @ApiResponse(responseCode = "400", description = "Failed validation",content = {@Content(mediaType = "application/json",schema = @Schema())})})
    @PostMapping("/forgotPassword")
    public ResponseEntity<?> forgotPassword(HttpServletRequest httpRequest,@Valid @RequestBody EmailRequest emailRequest){
        String token = passwordResetTokenCustomerService.createOrUpdatePasswordResetToken(emailRequest);
        mailService.sendToken(token,emailRequest.getEmail(),httpRequest);
        return new ResponseEntity<>(new PasswordResetTokenResponse(token),HttpStatus.OK);
    }
    @Operation(summary = "Change password", description = "Set new password after customer received email with password reset token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Failed password validation or password reset token expired",content = {@Content(mediaType = "application/json",schema = @Schema())})})
    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@Parameter(name = "token", description = "Password reset token", example = "b8aa464c-7375-464e-9d8f-83cdae970921")
                                     @RequestParam("token") String token,
                                     @Valid @RequestBody ChangePasswordRequest changePasswordRequest){
        if(passwordResetTokenCustomerService.validatePasswordResetToken(token)){
            passwordResetTokenCustomerService.updatePassword(changePasswordRequest, token);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
