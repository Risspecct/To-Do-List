package users.rishik.toDoList.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import users.rishik.toDoList.Dtos.ListDto;
import users.rishik.toDoList.Exceptions.NotFoundException;
import users.rishik.toDoList.Repositories.UserRepository;
import users.rishik.toDoList.entities.TaskList;
import users.rishik.toDoList.entities.User;

@Component
public class ListMapper {
    private final UserRepository userRepository;

    @Autowired
    public ListMapper(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public TaskList mapToModel(long userId, ListDto dto){
        TaskList list = new TaskList();
        User user = this.userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        list.setName(dto.getName());
        list.setDescription(dto.getDescription());
        list.setUser(user);
        return list;
    }
}
