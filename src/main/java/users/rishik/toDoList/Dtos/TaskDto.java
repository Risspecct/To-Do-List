package users.rishik.toDoList.Dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import users.rishik.toDoList.enums.Priority;
import users.rishik.toDoList.enums.Status;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TaskDto {
    @NotBlank
    private String description;

    @NotNull
    private LocalDateTime dueDate;

    private Priority priority = Priority.MEDIUM;

    private Status status = Status.PENDING;
}
