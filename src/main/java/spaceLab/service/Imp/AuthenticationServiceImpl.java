package spaceLab.service.Imp;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spaceLab.entity.Customer;
import spaceLab.entity.Invitation;
import spaceLab.model.authentication.AuthenticationRequest;
import spaceLab.model.authentication.AuthenticationResponse;
import spaceLab.model.authentication.RefreshToken;
import spaceLab.model.customer.CustomerRequest;
import spaceLab.repository.CustomerRepository;
import spaceLab.repository.InvitationRepository;
import spaceLab.service.AuthenticationService;
import spaceLab.service.JwtService;


@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    @Override
    public AuthenticationResponse register(CustomerRequest customerRequest) {
        Customer customer = new Customer();
        customer.setEmail(customerRequest.getEmail());
        customer.setName(customerRequest.getName());
        customer.setPhoneNumber(customerRequest.getPhoneNumber());
        customer.setPassword(passwordEncoder.encode(customerRequest.getPassword()));
        customer = customerRepository.save(customer);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setAccessToken(jwtService.generateAccessToken(customer));
        authenticationResponse.setRefreshToken(jwtService.generateRefreshToken(customer));
        return authenticationResponse;
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),authenticationRequest.getPassword()
                ));
        Customer Customer = customerRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow(EntityNotFoundException::new);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setAccessToken(jwtService.generateAccessToken(Customer));
        authenticationResponse.setRefreshToken(jwtService.generateRefreshToken(Customer));
        return authenticationResponse;
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshToken refreshToken) {
        String email = jwtService.extractCustomerEmail(refreshToken.getRefreshToken());
        if(email != null){
            Customer Customer = customerRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
            if(jwtService.isTokenValid(refreshToken.getRefreshToken(), Customer)){
                AuthenticationResponse authenticationResponse = new AuthenticationResponse();
                authenticationResponse.setAccessToken(jwtService.generateAccessToken(Customer));
                authenticationResponse.setRefreshToken(refreshToken.getRefreshToken());
                return authenticationResponse;
            }
        }
        return null;
    }
}
