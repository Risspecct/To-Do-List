package users.rishik.toDoList.Dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import users.rishik.toDoList.annotations.NullOrNotBlank;
import users.rishik.toDoList.enums.Priority;
import users.rishik.toDoList.enums.Status;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UpdateTaskDto {

    @NullOrNotBlank
    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime dueDate;

    private Priority priority;

    private Status status;
}
