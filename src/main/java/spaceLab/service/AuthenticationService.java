package spaceLab.service;


import spaceLab.model.authentication.AuthenticationRequest;
import spaceLab.model.authentication.AuthenticationResponse;
import spaceLab.model.authentication.RefreshToken;
import spaceLab.model.customer.CustomerRequest;


public interface AuthenticationService {
    AuthenticationResponse register(CustomerRequest customerRequest);
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
    AuthenticationResponse refreshToken(RefreshToken refreshToken);
}
