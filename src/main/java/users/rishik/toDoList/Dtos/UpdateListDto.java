package users.rishik.toDoList.Dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateListDto {
    private String name;

    private String description;

    private long userId;
}
