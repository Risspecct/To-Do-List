package users.rishik.toDoList.Controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import users.rishik.toDoList.Dtos.ListDto;
import users.rishik.toDoList.Dtos.UpdateListDto;
import users.rishik.toDoList.Exceptions.NotFoundException;
import users.rishik.toDoList.Services.ListService;

@RestController
@RequestMapping("/users/{userId}/list")
public class ListController {
    private final ListService listService;

    @Autowired
    public ListController(ListService listService){
        this.listService = listService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addList(@RequestBody @Valid ListDto dto, @PathVariable long userId){
        try {
            return new ResponseEntity<>(this.listService.addList(dto, userId), HttpStatus.CREATED);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/find/{listId}")
    public ResponseEntity<?> findList(@PathVariable long userId, @PathVariable long listId){
        try {
            return ResponseEntity.ok(this.listService.findList(userId, listId));
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllLists(@PathVariable long userId){
        try {
            return ResponseEntity.ok(this.listService.getUserLists(userId));
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/update/{listId}")
    public ResponseEntity<?> updateList(@PathVariable long userId, @PathVariable long listId, @RequestBody @Valid UpdateListDto dto){
        try {
            return ResponseEntity.ok(this.listService.updateList(userId, listId, dto));
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{listId}")
    public ResponseEntity<?> deleteList(@PathVariable long userId, @PathVariable long listId){
        try{
            this.listService.deleteList(userId, listId);
            return ResponseEntity.ok().body("Deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
