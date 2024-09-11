package spaceLab.mapper;

import org.mapstruct.Mapper;
import spaceLab.entity.Customer;
import spaceLab.model.customer.CustomerResponse;


@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerResponse customerToCustomerResponse(Customer customer);
}
