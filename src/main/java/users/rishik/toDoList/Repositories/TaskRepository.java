package users.rishik.toDoList.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import users.rishik.toDoList.entities.Task;
import users.rishik.toDoList.projections.TaskView;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<TaskView> findAllByTaskListId(long listId);
    Optional<TaskView> findProjectById(long taskId);
}
