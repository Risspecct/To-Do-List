package users.rishik.toDoList.Controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import users.rishik.toDoList.Dtos.TaskDto;
import users.rishik.toDoList.Dtos.UpdateTaskDto;
import users.rishik.toDoList.Exceptions.NotFoundException;
import users.rishik.toDoList.Exceptions.UnauhorizedAccessException;
import users.rishik.toDoList.Services.TaskService;

@RestController
@RequestMapping("/users/{userId}/lists/{listId}/tasks")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addTask(@PathVariable long userId, @PathVariable long listId, @RequestBody @Valid TaskDto dto){
        try {
            return new ResponseEntity<>(this.taskService.addTask(userId, listId, dto), HttpStatus.CREATED);
        }catch (UnauhorizedAccessException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/find/{taskId}")
    public ResponseEntity<?> getTask(@PathVariable long userId, @PathVariable long listId, @PathVariable long taskId){
        try {
            return ResponseEntity.ok(this.taskService.getTask(userId, listId, taskId));
        } catch (UnauhorizedAccessException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }  catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/find/all")
    public ResponseEntity<?> getAll(@PathVariable long userId, @PathVariable long listId){
        try {
            return ResponseEntity.ok(this.taskService.getAllTasks(userId, listId));
        } catch (UnauhorizedAccessException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/update/{taskId}")
    public ResponseEntity<?> updateTask(@PathVariable long userId, @PathVariable long listId,
                                        @PathVariable long taskId, @Valid @RequestBody UpdateTaskDto dto) {
        try {
            return new ResponseEntity<>(this.taskService.updateTask(userId, listId, taskId, dto), HttpStatus.ACCEPTED);
        } catch (UnauhorizedAccessException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity<?> deleteTask(
            @PathVariable long userId, @PathVariable long listId, @PathVariable long taskId){
        try {
            this.taskService.deleteTask(userId, listId, taskId);
            return ResponseEntity.ok("Deleted Successfully");
        } catch (UnauhorizedAccessException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }  catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}
