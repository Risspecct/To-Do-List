package users.rishik.toDoList.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import users.rishik.toDoList.entities.TaskList;
import users.rishik.toDoList.projections.TaskListView;

import java.util.List;
import java.util.Optional;

public interface TaskListRepository extends JpaRepository<TaskList, Long> {
    List<TaskListView> findAllByUserId(long userId);
    Optional<TaskListView> findProjectById(long listId);
}
