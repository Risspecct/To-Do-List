package users.rishik.toDoList.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;
import users.rishik.toDoList.Dtos.UserDto;
import users.rishik.toDoList.Exceptions.NotFoundException;
import users.rishik.toDoList.Services.UserService;
import users.rishik.toDoList.entities.User;
import users.rishik.toDoList.projections.UserView;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody @Valid User user){
        try {
            user.setPassword(encoder.encode(user.getPassword()));
            return new ResponseEntity<>(this.userService.addUser(user), HttpStatus.CREATED);
        } catch (DataIntegrityViolationException ex) {
            return new ResponseEntity<>(Map.of("Error:", "Email already exists!"), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/find/{userId}")
    public ResponseEntity<?> findUser(@PathVariable long userId){
        try {
            return ResponseEntity.ok(this.userService.findUser(userId));
        } catch (NotFoundException e){
            return new ResponseEntity<>(Map.of("Error", e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public List<User> getAllUsers(){
        return this.userService.findAllUsers();
    }

    @GetMapping("/csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request){
        return (CsrfToken) request.getAttribute("_csrf");
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable long userId, @RequestBody @Valid UserDto user){
        try {
            return new ResponseEntity<>(this.userService.updateUser(user, userId), HttpStatus.ACCEPTED);
        } catch (NotFoundException e){
            return new ResponseEntity<>(Map.of("Error", e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable long userId){
        try {
            this.userService.deleteUser(userId);
            return ResponseEntity.ok().body("User deleted successfully");
        }  catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
