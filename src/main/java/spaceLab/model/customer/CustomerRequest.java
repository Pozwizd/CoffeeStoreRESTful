package spaceLab.model.customer;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import spaceLab.validation.emailValidation.FieldEmailUnique;
import spaceLab.validation.phoneNumberValidation.FieldPhoneUnique;


/**
 * {@link spaceLab.entity.Customer} request model.
 */
@Data
public class CustomerRequest {
    private Long id;
    @NotEmpty(message = "Поле не может быть пустым")
    @Size(max=25, message = "Размер поля должен быть не более 25 символов")
    @Schema(example = "Аркадий")
    private String name;
    @NotEmpty(message = "Поле не может быть пустым")
    @Size(max=13, message = "Размер номера должен быть не более 13 символов")
    @Pattern(regexp = "\\+380(50|66|95|99|67|68|96|97|98|63|93|73)[0-9]{7}", message = "Неверный формат номера")
    @FieldPhoneUnique
    @Schema(example = "+380124567890")
    private String phoneNumber;
    @NotEmpty(message = "Поле не может быть пустым")
    @Size(max=100, message = "Размер поля должен быть не более 50 символов")
    @Email(regexp = "[a-zA-Z0-9._-]+@[a-zA-Z0-9-]+\\.[a-z]{2,3}", message = "Неверный формат email")
    @FieldEmailUnique
    @Schema(example = "user@example.com")
    private String email;
    @Size(max=100, message = "Размер поля должен быть не более 100 символов")
    @Pattern.List({
            @Pattern(regexp = ".{8,}", message = "Пароль должен содержать по крайней мере одну цифру, одну заглавную букву, один спецсимвол ,./? и размер больше 8"),
            @Pattern(regexp = ".*\\d+.*", message = "Пароль должен содержать по крайней мере одну цифру, одну заглавную букву, один спецсимвол ,./? и размер больше 8"),
            @Pattern(regexp = ".*[,./?]+.*", message = "Пароль должен содержать по крайней мере одну цифру, одну заглавную букву, один спецсимвол ,./? и размер больше 8"),
            @Pattern(regexp = ".*[A-Z]+.*", message = "Пароль должен содержать по крайней мере одну цифру, одну заглавную букву, один спецсимвол ,./? и размер больше 8")
    })
    @Schema(example = "admin")
    private String password;


    private String token;
}
