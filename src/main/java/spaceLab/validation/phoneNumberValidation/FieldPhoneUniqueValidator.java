package spaceLab.validation.phoneNumberValidation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import spaceLab.entity.Customer;
import spaceLab.repository.CustomerRepository;

import java.util.Optional;

public class FieldPhoneUniqueValidator implements ConstraintValidator<FieldPhoneUnique, String> {
    private final CustomerRepository userRepository;

    public FieldPhoneUniqueValidator(CustomerRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        Optional<Customer> user = userRepository.findByPhoneNumber(s);
        return user.isEmpty();
    }
}
