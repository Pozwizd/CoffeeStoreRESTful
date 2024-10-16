package spaceLab.service.Imp;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spaceLab.entity.Customer;
import spaceLab.mapper.CustomerMapper;
import spaceLab.model.customer.CustomerProfileRequest;
import spaceLab.model.customer.CustomerResponse;
import spaceLab.repository.CustomerRepository;
import spaceLab.service.CustomerService;

@Service
@AllArgsConstructor
@Slf4j
public class CustomerServiceImp implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public CustomerResponse updateCustomerFromCustomerRequest(CustomerProfileRequest customerRequest) {
        Customer customer = customerRepository.findById(customerRequest.getId()).map(customer1 -> {
            customer1.setEmail(customerRequest.getEmail());
            customer1.setName(customerRequest.getName());
            customer1.setPhoneNumber(customerRequest.getPhoneNumber());
            customer1.setLanguage(customerRequest.getLanguage());
            if(passwordEncoder.encode(customer1.getPassword()).equals(customerRequest.getOldPassword())){
                if (customerRequest.getNewPassword().equals(customerRequest.getConfirmNewPassword())){
                    customer1.setPassword(passwordEncoder.encode(customerRequest.getNewPassword()));
                }
            }
            customerRepository.save(customer1);
            return customer1;
        }).orElse(null);
        if (customer == null) {
            log.info("Customer not found");
            return null;
        }
        log.info("Customer updated");
        return customerMapper.customerToCustomerResponse(customer);
    }

    @Override
    public Customer getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    @Override
    public CustomerResponse getCustomerResponseByEmail(String email) {
        return customerMapper.customerToCustomerResponse(getCustomerByEmail(email));
    }

}
