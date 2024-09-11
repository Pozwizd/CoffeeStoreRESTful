package spaceLab.validation.emailValidation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

@Constraint(validatedBy = EmailUniqueValidator.class)
@Target({TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailUnique {
    String id();
    String email();
    String message() default "Такай адрес электронной почты уже зарегистрирован";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
