package spaceLab.model.customer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import spaceLab.entity.CustomerStatus;
import spaceLab.entity.Language;

import java.time.LocalDate;

/**
 * {@link spaceLab.entity.Customer} response model.
 */
@Data
public class CustomerResponse {

    @Schema(example = "1")
    private Long id;
    @Schema(example = "Артур")
    private String name;
    @Schema(example = "user@example.com")
    private String email;
    @Schema(example = "2000-02-15")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    @Schema(example = "Street 1, City, Country")
    private String address;
    @Schema(example = "+380123456789")
    private String phoneNumber;
    @Schema(example = "RU")
    private Language language;
    @Schema(example = "ACTIVE")
    private CustomerStatus status;
}