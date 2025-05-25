package users.rishik.toDoList.Dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import users.rishik.toDoList.annotations.NullOrNotBlank;

@Data
@NoArgsConstructor
public class UserDto {

    @NullOrNotBlank
    private String name;

    @NullOrNotBlank
    private String email;

    @NullOrNotBlank
    private String password;
}
