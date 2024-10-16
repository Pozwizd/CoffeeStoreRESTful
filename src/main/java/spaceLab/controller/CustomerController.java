package spaceLab.controller;

import com.google.zxing.WriterException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import spaceLab.entity.Language;
import spaceLab.model.InviteLinks;
import spaceLab.model.customer.CustomerProfileRequest;
import spaceLab.model.customer.CustomerRequest;
import spaceLab.model.customer.CustomerResponse;
import spaceLab.service.CustomerService;
import spaceLab.service.InvitationService;
import spaceLab.service.Imp.QRCodeGeneratorService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
CustomerController
- customerService: CustomerService
--
+ getCustomerProfile(): ResponseEntity<CustomerDto>
+ updateCustomerProfile(@Valid @RequestBody CustomerDto customerDto): ResponseEntity<CustomerDto>
+ getLanguages(): ResponseEntity<List<Language>>
 + deleteCustomerProfile(): ResponseEntity<?>
 */
@Tag(name = "Profile")
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class CustomerController {
    private final CustomerService customerService;
    private final InvitationService invitationService;
    private final QRCodeGeneratorService qrCodeGeneratorService;


    @Operation(summary = "Generate invite link", description = "Generates a permanent invite link for the customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema())}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {@Content(mediaType = "application/json", schema = @Schema())}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(mediaType = "application/json", schema = @Schema())})
    })
    @GetMapping("/generateInviteLink")
    public ResponseEntity<InviteLinks> generateInviteLink(HttpServletRequest request) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUsername();

        Customer inviter = customerService.getCustomerByEmail(email);

        Invitation invitation = invitationService.findByInviterAndIsActive(inviter, true);
        if (invitation == null) {
            invitation = new Invitation();
            invitation.setInviteCode(UUID.randomUUID().toString());
            invitation.setInviter(inviter);
            invitation.setActive(true);
            invitationService.saveInvitation(invitation);
        }

        String inviteCode = invitation.getInviteCode();
        String baseLink = request.getRequestURL().toString().replace("/generateInviteLink", "/register?inviteCode=") + inviteCode;

        String telegramLink = "https://t.me/share/url?url=" + baseLink;
        String viberLink = "viber://forward?text=" + baseLink;

        byte[] baseLinkQR = generateQRCode(baseLink);


        InviteLinks inviteLinks = new InviteLinks(baseLink, telegramLink, viberLink, baseLinkQR);

        return new ResponseEntity<>(inviteLinks, HttpStatus.OK);
    }

    private byte[] generateQRCode(String link) {
        try {
            return qrCodeGeneratorService.generateQRCodeImage(link, 350, 350);
        } catch (WriterException | IOException e) {
            throw new RuntimeException("Ошибка генерации QR-кода", e);
        }
    }

    @Operation(summary = "Get customer profile",description = "Getting customer profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = CustomerResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Customer unauthorized",content = {@Content(mediaType = "application/json",schema = @Schema())}),
            @ApiResponse(responseCode = "400", description = "Bad request",content = {@Content(mediaType = "application/json",schema = @Schema())})})
    @GetMapping("/profile")
    public ResponseEntity<CustomerResponse> getCustomerProfile(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String email = userDetails.getUsername();
        CustomerResponse customerDtoByEmail = customerService.getCustomerResponseByEmail(email);
        return new ResponseEntity<>(customerDtoByEmail, HttpStatus.OK);
    }

    @Operation(summary = "Get languages",description = "Get languages for select")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = Language.class))}),
            @ApiResponse(responseCode = "401", description = "Customer unauthorized",content = {@Content(mediaType = "application/json",schema = @Schema())}),
            @ApiResponse(responseCode = "400", description = "Bad request",content = {@Content(mediaType = "application/json",schema = @Schema())})})
    @GetMapping("/languages")
    public List<Language> getLanguages(){
        return List.of(Language.values());

    }

    @Operation(summary = "Update profile",description = "Updating customer profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = CustomerRequest.class))}),
            @ApiResponse(responseCode = "401", description = "Customer unauthorized",content = {@Content(mediaType = "application/json",schema = @Schema())}),
            @ApiResponse(responseCode = "404", description = "Customer not found",content = {@Content(mediaType = "application/json",schema = @Schema())}),
            @ApiResponse(responseCode = "400", description = "Failed validation",content = {@Content(mediaType = "application/json",schema = @Schema())})})
    @PutMapping("/profile")
    public ResponseEntity<?> updateCustomerProfile(@Valid @RequestBody CustomerProfileRequest customerRequest){
        CustomerResponse customerResponseByEmail = customerService.updateCustomerFromCustomerRequest(customerRequest);

        return new ResponseEntity<>(customerResponseByEmail, HttpStatus.OK);
    }

}
