package users.rishik.toDoList.Controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import users.rishik.toDoList.Dtos.UserDto;
import users.rishik.toDoList.Exceptions.NotFoundException;
import users.rishik.toDoList.Services.UserService;
import users.rishik.toDoList.entities.User;
import java.util.Map;


@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody @Valid User user){
        try {
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

    @PutMapping("/update/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable long userId, @RequestBody UserDto user){
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
