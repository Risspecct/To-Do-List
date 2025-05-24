package users.rishik.toDoList.Dtos;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class UserDto {
    private String name;

    private String email;

    private String password;
}
