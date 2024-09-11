package spaceLab.service;

import org.springframework.stereotype.Service;
import spaceLab.entity.Customer;
import spaceLab.model.customer.CustomerProfileRequest;
import spaceLab.model.customer.CustomerResponse;


@Service
public interface CustomerService {

    CustomerResponse updateCustomerFromCustomerRequest(CustomerProfileRequest customerRequest);
    Customer getCustomerByEmail(String email);
    CustomerResponse getCustomerResponseByEmail(String email);
}
