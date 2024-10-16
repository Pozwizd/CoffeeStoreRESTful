package spaceLab.service.Imp;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import spaceLab.entity.Customer;
import spaceLab.model.authentication.AuthenticationRequest;
import spaceLab.model.authentication.AuthenticationResponse;
import spaceLab.model.authentication.RefreshToken;
import spaceLab.model.customer.CustomerRequest;
import spaceLab.repository.CustomerRepository;
import spaceLab.service.JwtService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthenticationServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    private Customer customer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        customer = new Customer();
        customer.setEmail("test@example.com");
        customer.setPassword("password");
        customer.setName("John Doe");
        customer.setPhoneNumber("1234567890");
    }

    @Test
    public void testRegister_Success() {
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setEmail("test@example.com");
        customerRequest.setPassword("password");
        customerRequest.setName("John Doe");
        customerRequest.setPhoneNumber("1234567890");

        when(passwordEncoder.encode(customerRequest.getPassword())).thenReturn("encodedPassword");
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        when(jwtService.generateAccessToken(any(Customer.class))).thenReturn("accessToken");
        when(jwtService.generateRefreshToken(any(Customer.class))).thenReturn("refreshToken");

        AuthenticationResponse response = authenticationService.register(customerRequest);

        assertNotNull(response);
        assertEquals("accessToken", response.getAccessToken());
        assertEquals("refreshToken", response.getRefreshToken());

        verify(customerRepository, times(1)).save(any(Customer.class));
        verify(jwtService, times(1)).generateAccessToken(any(Customer.class));
        verify(jwtService, times(1)).generateRefreshToken(any(Customer.class));
    }

    @Test
    public void testAuthenticate_Success() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setEmail("test@example.com");
        authenticationRequest.setPassword("password");

        when(customerRepository.findByEmail("test@example.com")).thenReturn(Optional.of(customer));
        when(jwtService.generateAccessToken(any(Customer.class))).thenReturn("accessToken");
        when(jwtService.generateRefreshToken(any(Customer.class))).thenReturn("refreshToken");

        AuthenticationResponse response = authenticationService.authenticate(authenticationRequest);

        assertNotNull(response);
        assertEquals("accessToken", response.getAccessToken());
        assertEquals("refreshToken", response.getRefreshToken());

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(customerRepository, times(1)).findByEmail("test@example.com");
        verify(jwtService, times(1)).generateAccessToken(any(Customer.class));
        verify(jwtService, times(1)).generateRefreshToken(any(Customer.class));
    }

    @Test
    public void testAuthenticate_CustomerNotFound() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setEmail("nonexistent@example.com");
        authenticationRequest.setPassword("password");

        when(customerRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            authenticationService.authenticate(authenticationRequest);
        });

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(customerRepository, times(1)).findByEmail("nonexistent@example.com");
    }

    @Test
    public void testRefreshToken_Success() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setRefreshToken("validRefreshToken");

        when(jwtService.extractCustomerEmail("validRefreshToken")).thenReturn("test@example.com");
        when(customerRepository.findByEmail("test@example.com")).thenReturn(Optional.of(customer));
        when(jwtService.isTokenValid("validRefreshToken", customer)).thenReturn(true);
        when(jwtService.generateAccessToken(customer)).thenReturn("newAccessToken");

        AuthenticationResponse response = authenticationService.refreshToken(refreshToken);

        assertNotNull(response);
        assertEquals("newAccessToken", response.getAccessToken());
        assertEquals("validRefreshToken", response.getRefreshToken());

        verify(jwtService, times(1)).extractCustomerEmail("validRefreshToken");
        verify(customerRepository, times(1)).findByEmail("test@example.com");
        verify(jwtService, times(1)).isTokenValid("validRefreshToken", customer);
        verify(jwtService, times(1)).generateAccessToken(customer);
    }

    @Test
    public void testRefreshToken_InvalidToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setRefreshToken("invalidRefreshToken");

        when(jwtService.extractCustomerEmail("invalidRefreshToken")).thenReturn(null);

        AuthenticationResponse response = authenticationService.refreshToken(refreshToken);

        assertNull(response);

        verify(jwtService, times(1)).extractCustomerEmail("invalidRefreshToken");
        verify(customerRepository, times(0)).findByEmail(anyString());
        verify(jwtService, times(0)).isTokenValid(anyString(), any(Customer.class));
    }
}
