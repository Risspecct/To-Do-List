package users.rishik.toDoList.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import users.rishik.toDoList.Dtos.TaskDto;
import users.rishik.toDoList.Dtos.UpdateTaskDto;
import users.rishik.toDoList.Exceptions.NotFoundException;
import users.rishik.toDoList.Exceptions.UnauhorizedAccessException;
import users.rishik.toDoList.Repositories.TaskListRepository;
import users.rishik.toDoList.Repositories.TaskRepository;
import users.rishik.toDoList.entities.Task;
import users.rishik.toDoList.entities.TaskList;
import users.rishik.toDoList.mappers.TaskMapper;
import users.rishik.toDoList.projections.TaskView;

import java.util.List;

@Service
public class TaskService {
    private final TaskListRepository taskListRepository;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Autowired
    TaskService(TaskListRepository taskListRepository, TaskRepository taskRepository, TaskMapper taskMapper){
        this.taskListRepository = taskListRepository;
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    public Task addTask(long userId, long listId, TaskDto dto){
        this.validate(userId, listId);
        Task task = taskMapper.mapToModel(dto, listId);
        return this.taskRepository.save(task);
    }

    public TaskView getTask(long userId, long listId, long taskId){
        this.validate(userId, listId);
        return this.taskRepository.findProjectById(taskId).orElseThrow(() -> new NotFoundException("No task with Id: " + taskId));
    }

    public List<TaskView> getAllTasks(long userId, long listId){
        this.validate(userId, listId);
        List<TaskView> tasks = this.taskRepository.findAllByTaskListId(listId);
        if (tasks.isEmpty()) throw new NotFoundException("No tasks present in this list");
        return tasks;
    }

    public Task updateTask(long userId, long listId, long taskId, UpdateTaskDto dto){
        this.validate(userId, listId);
        Task task = this.taskRepository.findById(taskId).orElseThrow(() -> new NotFoundException("No ask found with id: taskId"));
        if (dto.getDescription() != null) task.setDescription(dto.getDescription());
        if (dto.getDueDate() != null) task.setDueDate(dto.getDueDate());
        if (dto.getStatus() != null) task.setStatus(dto.getStatus());
        if (dto.getPriority() != null) task.setPriority(dto.getPriority());
        if (dto.getCreatedAt() != null) task.setCreatedAt(dto.getCreatedAt());
        return this.taskRepository.save(task);
    }

    public void deleteTask(long userId, long listId, long taskId){
        this.validate(userId,listId);
        this.taskRepository.deleteById(taskId);
    }

    private void validate(long userId, long listId){
        TaskList list = this.taskListRepository.findById(listId)
                .orElseThrow(() -> new NotFoundException("No List found with id: " + listId));
        if (list.getUser().getId() != userId) throw new UnauhorizedAccessException("You are not authorized to access this list");
    }
}
