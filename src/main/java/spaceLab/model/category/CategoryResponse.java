package spaceLab.model.category;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class CategoryResponse {

    private Long id;
    @NotEmpty(message = "Название не может быть пустым")
    private String name;
    private Integer itemsInCategory;
    private String status;
}
