package users.rishik.toDoList.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import users.rishik.toDoList.Dtos.ListDto;
import users.rishik.toDoList.Dtos.UpdateListDto;
import users.rishik.toDoList.Exceptions.NotFoundException;
import users.rishik.toDoList.Exceptions.UnauhorizedAccessException;
import users.rishik.toDoList.Repositories.TaskListRepository;
import users.rishik.toDoList.entities.TaskList;
import users.rishik.toDoList.mappers.ListMapper;
import users.rishik.toDoList.projections.TaskListView;

import java.util.List;

@Service
public class ListService {
    private final TaskListRepository taskListRepository;
    private final ListMapper listMapper;

    @Autowired
    public ListService(TaskListRepository taskListRepository, ListMapper listMapper){
        this.taskListRepository = taskListRepository;
        this.listMapper = listMapper;
    }

    public TaskList addList(ListDto dto, long userId){
        TaskList taskList = this.listMapper.mapToModel(userId, dto);
        return this.taskListRepository.save(taskList);
    }

    public TaskListView findList(long userId, long listId){
        this.validate(userId, listId);
        return this.taskListRepository.findProjectById(listId).orElseThrow(() -> new NotFoundException("No list with Id: " + listId));
    }

    public List<TaskListView> getUserLists(long userId){
        List<TaskListView> lists = this.taskListRepository.findAllByUserId(userId);
        if (lists.isEmpty()) throw new NotFoundException("No Lists found with user id:" + userId);
        else return lists;
    }

    public TaskList updateList(long userId, long listId, UpdateListDto dto){
        TaskList existingList = this.validate(userId, listId);
        if (dto.getName() != null) existingList.setName(dto.getName());
        if (dto.getDescription() != null) existingList.setDescription(dto.getDescription());
        return this.taskListRepository.save(existingList);
    }

    public void deleteList(long userId, long listId){
        this.validate(userId, listId);
        this.taskListRepository.deleteById(listId);
    }

    public TaskList validate(long userId,long listId){
        TaskList list = this.taskListRepository.findById(listId).orElseThrow(() -> new NotFoundException("No list with Id: " + listId));
        if (list.getUser().getId() != userId) throw new UnauhorizedAccessException("You are not authorized to access this list");
        return list;
    }
}
