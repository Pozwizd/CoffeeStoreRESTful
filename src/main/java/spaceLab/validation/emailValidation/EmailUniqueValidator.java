package spaceLab.validation.emailValidation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanWrapperImpl;
import spaceLab.entity.Customer;
import spaceLab.model.customer.CustomerProfileRequest;
import spaceLab.service.CustomerService;

import java.util.Optional;

@AllArgsConstructor
public class EmailUniqueValidator implements ConstraintValidator<EmailUnique, CustomerProfileRequest> {

    private final CustomerService customerService;


    @Override
    public void initialize(EmailUnique constraintAnnotation) {
    }

    @Override
    public boolean isValid(CustomerProfileRequest customerProfileRequest, ConstraintValidatorContext constraintValidatorContext) {
        if (customerProfileRequest == null || customerProfileRequest.getEmail() == null || customerProfileRequest.getEmail().isEmpty()) {
            return true;
        }

        Customer existingUser = customerService.getCustomerByEmail(customerProfileRequest.getEmail());
        if (existingUser != null && !existingUser.getId().equals(customerProfileRequest.getId())) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Email уже используется")
                    .addPropertyNode("email")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }


}
