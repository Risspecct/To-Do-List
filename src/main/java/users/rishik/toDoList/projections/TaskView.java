package users.rishik.toDoList.projections;

import users.rishik.toDoList.enums.Priority;
import users.rishik.toDoList.enums.Status;

import java.time.LocalDateTime;

public interface TaskView {
    Long getId();
    String getDescription();
    LocalDateTime getDueDate();
    Priority getPriority();
    Status getStatus();
}
