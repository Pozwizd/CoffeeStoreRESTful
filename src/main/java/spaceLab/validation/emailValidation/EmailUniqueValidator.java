package spaceLab.validation.emailValidation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanWrapperImpl;
import spaceLab.entity.Customer;
import spaceLab.service.CustomerService;

@AllArgsConstructor
public class EmailUniqueValidator implements ConstraintValidator<EmailUnique, Object> {
    private final CustomerService customerService;

    private String email;

    @Override
    public void initialize(EmailUnique constraintAnnotation) {
        this.email = constraintAnnotation.email();
    }

    @Override
    public boolean isValid(Object s, ConstraintValidatorContext constraintValidatorContext) {
        Object emailValue = new BeanWrapperImpl(s).getPropertyValue(email);
        Customer byEmail = customerService.getCustomerByEmail(emailValue.toString());
        return byEmail != null;
    }
}
