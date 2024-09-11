package spaceLab.model.authentication;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthenticationRequest {
    @NotEmpty(message = "Поле не может быть пустым")
    @Schema(example = "darin.senger@yahoo.com")
    private String email;
    @NotEmpty(message = "Поле не может быть пустым")
    @Schema(example = "054yx8i9aqm8920")
    private String password;

}
