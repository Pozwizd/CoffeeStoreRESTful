package spaceLab.validation.phoneNumberValidation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
@Constraint(validatedBy = FieldPhoneUniqueValidator.class)
@Target({FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldPhoneUnique {
    String message() default "Такой номер телефона уже существует";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
