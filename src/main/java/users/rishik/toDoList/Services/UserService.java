package users.rishik.toDoList.Services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import users.rishik.toDoList.Dtos.UserDto;
import users.rishik.toDoList.Exceptions.NotFoundException;
import users.rishik.toDoList.Repositories.UserRepository;
import users.rishik.toDoList.entities.User;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, AuthenticationManager authManager, JwtService jwtService){
        this.userRepository = userRepository;
        this.authManager = authManager;
        this.jwtService = jwtService;
    }

    public User addUser(User user){
        return this.userRepository.save(user);
    }

    public User findUser(long userId){
        return this.userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with id :" + userId +" not found"));
    }

    public List<User> findAllUsers(){
        return this.userRepository.findAll();
    }

    public User updateUser(UserDto user, long userId){
        User existingUser = this.findUser(userId);
        if (user.getName() != null) existingUser.setName(user.getName());
        if (user.getEmail() != null) existingUser.setEmail(user.getEmail());
        if (user.getPassword() != null) existingUser.setPassword(user.getPassword());
        return this.userRepository.save(existingUser);
    }

    public void deleteUser(long userId){
        this.userRepository.deleteById(userId);
    }


    public String verify(User user){
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            User existingUser = this.userRepository.findByName(user.getName());
            return jwtService.generateToken(existingUser);
        }
        else return "Login Failed";
    }
}
