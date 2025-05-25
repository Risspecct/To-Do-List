package users.rishik.toDoList.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import users.rishik.toDoList.entities.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByTaskListId(long listId);
}
