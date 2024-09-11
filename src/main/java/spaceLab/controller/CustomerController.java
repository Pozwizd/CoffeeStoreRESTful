package spaceLab.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import spaceLab.entity.Language;
import spaceLab.model.customer.CustomerProfileRequest;
import spaceLab.model.customer.CustomerRequest;
import spaceLab.model.customer.CustomerResponse;
import spaceLab.service.CustomerService;

import java.util.List;

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
