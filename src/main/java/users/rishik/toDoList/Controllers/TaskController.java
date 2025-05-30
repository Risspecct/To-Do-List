package users.rishik.toDoList.Controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import users.rishik.toDoList.Dtos.TaskDto;
import users.rishik.toDoList.Dtos.UpdateTaskDto;
import users.rishik.toDoList.Exceptions.NotFoundException;
import users.rishik.toDoList.Exceptions.UnauhorizedAccessException;
import users.rishik.toDoList.Security.UserPrincipal;
import users.rishik.toDoList.Services.TaskService;

@RestController
@RequestMapping("/user/lists/{listId}/tasks")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addTask(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable long listId, @RequestBody @Valid TaskDto dto){
        try {
            return new ResponseEntity<>(this.taskService.addTask(userPrincipal.getUserId(), listId, dto), HttpStatus.CREATED);
        }catch (UnauhorizedAccessException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/find/{taskId}")
    public ResponseEntity<?> getTask(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable long listId, @PathVariable long taskId){
        try {
            return ResponseEntity.ok(this.taskService.getTask(userPrincipal.getUserId(), listId, taskId));
        } catch (UnauhorizedAccessException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }  catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/find/all")
    public ResponseEntity<?> getAll(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable long listId){
        try {
            return ResponseEntity.ok(this.taskService.getAllTasks(userPrincipal.getUserId(), listId));
        } catch (UnauhorizedAccessException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/update/{taskId}")
    public ResponseEntity<?> updateTask(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable long listId,
                                        @PathVariable long taskId, @Valid @RequestBody UpdateTaskDto dto) {
        try {
            return new ResponseEntity<>(this.taskService.updateTask(userPrincipal.getUserId(), listId, taskId, dto), HttpStatus.ACCEPTED);
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
            @AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable long listId, @PathVariable long taskId){
        try {
            this.taskService.deleteTask(userPrincipal.getUserId(), listId, taskId);
            return ResponseEntity.ok("Deleted Successfully");
        } catch (UnauhorizedAccessException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }  catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
