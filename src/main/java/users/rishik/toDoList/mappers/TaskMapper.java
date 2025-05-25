package users.rishik.toDoList.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import users.rishik.toDoList.Dtos.TaskDto;
import users.rishik.toDoList.Exceptions.NotFoundException;
import users.rishik.toDoList.Repositories.TaskListRepository;
import users.rishik.toDoList.entities.Task;
import users.rishik.toDoList.entities.TaskList;

@Component
public class TaskMapper {
    private final TaskListRepository taskListRepository;

    @Autowired
    TaskMapper(TaskListRepository taskListRepository){
        this.taskListRepository = taskListRepository;
    }

    public Task mapToModel(TaskDto dto, long listId){
        TaskList list = this.taskListRepository.findById(listId)
                .orElseThrow(()-> new NotFoundException("No list found with id: " + listId));
        Task task = new Task();

        task.setDescription(dto.getDescription());
        task.setDueDate(dto.getDueDate());
        task.setPriority(dto.getPriority());
        task.setStatus(dto.getStatus());
        task.setTaskList(list);

        return task;
    }
}
