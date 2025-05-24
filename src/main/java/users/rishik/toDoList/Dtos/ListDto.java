package users.rishik.toDoList.Dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ListDto {
    @NotBlank
    private String name;

    @Size(max=50)
    private String description;
}
