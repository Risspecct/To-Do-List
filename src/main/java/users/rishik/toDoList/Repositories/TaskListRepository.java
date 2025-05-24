package users.rishik.toDoList.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import users.rishik.toDoList.entities.TaskList;

import java.util.List;

public interface TaskListRepository extends JpaRepository<TaskList, Long> {
    List<TaskList> findAllByUserId(long userId);
}
