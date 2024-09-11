package spaceLab.model.customer;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import spaceLab.entity.Language;
import spaceLab.validation.confirmPassword.PasswordMatching;
import spaceLab.validation.emailValidation.EmailUnique;
import spaceLab.validation.newPasswordValidation.NewPasswordValid;
import spaceLab.validation.notEmptyPasswordValidation.NotEmptyOldPassword;
import spaceLab.validation.phoneNumberValidation.PhoneNumberUnique;
import spaceLab.validation.validateOldPassword.OldPasswordMatching;

import java.time.LocalDate;

@PasswordMatching(
        newPassword = "newPassword",
        confirmNewPassword = "confirmNewPassword",
        message = "Пароли должны совпадать"
)
@OldPasswordMatching(
        id = "id",
        oldPassword = "oldPassword",
        message = "Неверный пароль"
)
@EmailUnique(
        id = "id",
        email = "email"
)
@PhoneNumberUnique(
        id = "id",
        phoneNumber = "phoneNumber"
)
@NotEmptyOldPassword(
        oldPassword = "oldPassword",
        newPassword = "newPassword"
)
@Data
public class CustomerProfileRequest {
    @Schema(example = "1")
    private Long id;
    @NotEmpty(message = "Поле не может быть пустым")
    @Size(max = 25, message = "Размер поля должен быть не более 25 символов")
    @Schema(example = "User")
    private String name;
    @NotEmpty(message = "Поле не может быть пустым")
    @Size(max = 13, message = "Размер номера должен быть не более 13 символов")
    @Pattern(regexp = "\\+380(50|66|95|99|67|68|96|97|98|63|93|73)[0-9]{7}", message = "Неверный формат номера")
    @Schema(example = "+380123456789")
    private String phoneNumber;
    @NotEmpty(message = "Поле не может быть пустым")
    @Size(max = 100, message = "Размер поля должен быть не более 100 символов")
    @Email(regexp = "[a-zA-Z0-9._-]+@[a-zA-Z0-9-]+\\.[a-z]{2,3}", message = "Неверный формат email")
    @Schema(example = "user@example.com")
    private String email;
    @Schema(example = "1975-01-01")
    private LocalDate birthDate;
    private Language language;
    @Size(max = 100, message = "Размер поля должен быть не более 100 символов")
    @Schema(example = "password")
    private String oldPassword;
    @Size(max = 100, message = "Размер поля должен быть не более 100 символов")
    @NewPasswordValid
    @Schema(example = "password")
    private String newPassword;
    @Size(max = 100, message = "Размер поля должен быть не более 100 символов")
    @Schema(example = "password")
    private String confirmNewPassword;
}
