package spaceLab.validation.emailValidation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import spaceLab.service.CustomerService;

public class FieldEmailUniqueValidator implements ConstraintValidator<FieldEmailUnique,String> {
    private final CustomerService customerService;

    public FieldEmailUniqueValidator(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return customerService.getCustomerByEmail(s) == null;
    }
}
