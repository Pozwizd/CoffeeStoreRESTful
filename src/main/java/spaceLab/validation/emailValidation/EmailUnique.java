package spaceLab.validation.emailValidation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Constraint(validatedBy = EmailUniqueValidator.class)
@Target({TYPE, FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailUnique {
    String message() default "Такай адрес электронной почты уже используется";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
