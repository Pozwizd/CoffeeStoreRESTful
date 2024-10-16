package spaceLab.service.Imp;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import spaceLab.entity.Customer;
import spaceLab.entity.Language;
import spaceLab.mapper.CustomerMapper;
import spaceLab.model.customer.CustomerProfileRequest;
import spaceLab.model.customer.CustomerResponse;
import spaceLab.repository.CustomerRepository;

import java.util.Optional;

@Slf4j
public class CustomerServiceImpTest {

    @InjectMocks
    private CustomerServiceImp customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    private Customer customer;
    private CustomerProfileRequest customerProfileRequest;
    private CustomerResponse customerResponse;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        customer = new Customer();
        customer.setId(1L);
        customer.setEmail("test@example.com");
        customer.setName("Test User");
        customer.setPhoneNumber("1234567890");
        customer.setLanguage(Language.EN);
        customer.setPassword("oldPassword");

        customerProfileRequest = new CustomerProfileRequest();
        customerProfileRequest.setId(1L);
        customerProfileRequest.setEmail("updated@example.com");
        customerProfileRequest.setName("Updated User");
        customerProfileRequest.setPhoneNumber("0987654321");
        customerProfileRequest.setLanguage(Language.EN);
        customerProfileRequest.setOldPassword("oldPassword");
        customerProfileRequest.setNewPassword("newPassword");
        customerProfileRequest.setConfirmNewPassword("newPassword");

        customerResponse = new CustomerResponse();
        customerResponse.setId(1L);
        customerResponse.setEmail("updated@example.com");
        customerResponse.setName("Updated User");
        customerResponse.setPhoneNumber("0987654321");
        customerResponse.setLanguage(Language.EN);
    }

    @Test
    public void testUpdateCustomerFromCustomerRequest() {
        when(customerRepository.findById(customerProfileRequest.getId())).thenReturn(Optional.of(customer));

        when(passwordEncoder.encode(any(CharSequence.class))).thenAnswer(invocation -> {
            String password = invocation.getArgument(0).toString();
            return password;
        });

        when(customerMapper.customerToCustomerResponse(any(Customer.class))).thenReturn(customerResponse);

        CustomerResponse result = customerService.updateCustomerFromCustomerRequest(customerProfileRequest);

        assertNotNull(result);
        assertEquals("updated@example.com", result.getEmail());
        assertEquals("Updated User", result.getName());
        assertEquals("0987654321", result.getPhoneNumber());
        assertEquals("EN", result.getLanguage().toString());
        assertEquals("newPassword", customer.getPassword()); // Проверка, что пароль был обновлен
        verify(customerRepository, times(1)).findById(customerProfileRequest.getId());
        verify(customerRepository, times(1)).save(customer);
        log.info("Customer updated successfully");
    }


    @Test
    public void testUpdateCustomerFromCustomerRequest_CustomerNotFound() {
        when(customerRepository.findById(customerProfileRequest.getId())).thenReturn(Optional.empty());

        CustomerResponse result = customerService.updateCustomerFromCustomerRequest(customerProfileRequest);

        assertNull(result);
        verify(customerRepository, times(1)).findById(customerProfileRequest.getId());
        log.info("Customer not found");
    }

    @Test
    public void testGetCustomerByEmail() {
        when(customerRepository.findByEmail(customer.getEmail())).thenReturn(Optional.of(customer));

        Customer result = customerService.getCustomerByEmail(customer.getEmail());

        assertNotNull(result);
        assertEquals(customer.getEmail(), result.getEmail());
        verify(customerRepository, times(1)).findByEmail(customer.getEmail());
    }

    @Test
    public void testGetCustomerByEmail_CustomerNotFound() {
        when(customerRepository.findByEmail(customer.getEmail())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            customerService.getCustomerByEmail(customer.getEmail());
        });

        assertEquals("Customer not found", exception.getMessage());
        verify(customerRepository, times(1)).findByEmail(customer.getEmail());
    }

    @Test
    public void testGetCustomerResponseByEmail() {
        when(customerRepository.findByEmail(customer.getEmail())).thenReturn(Optional.of(customer));
        when(customerMapper.customerToCustomerResponse(customer)).thenReturn(customerResponse);

        CustomerResponse result = customerService.getCustomerResponseByEmail(customer.getEmail());

        assertNotNull(result);
        assertEquals(customerResponse.getId(), result.getId());
        verify(customerRepository, times(1)).findByEmail(customer.getEmail());
        verify(customerMapper, times(1)).customerToCustomerResponse(customer);
    }
}
