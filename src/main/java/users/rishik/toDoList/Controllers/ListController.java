package users.rishik.toDoList.Controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import users.rishik.toDoList.Dtos.ListDto;
import users.rishik.toDoList.Dtos.UpdateListDto;
import users.rishik.toDoList.Exceptions.NotFoundException;
import users.rishik.toDoList.Repositories.UserRepository;
import users.rishik.toDoList.Security.UserPrincipal;
import users.rishik.toDoList.Services.ListService;

@RestController
@RequestMapping("/user/list")
public class ListController {
private final ListService listService;
    private final UserRepository userRepository;

    @Autowired
    public ListController(ListService listService,
                          UserRepository userRepository){
        this.listService = listService;
        this.userRepository = userRepository;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addList(@RequestBody @Valid ListDto dto, @AuthenticationPrincipal UserPrincipal userPrincipal){
        try {
            return new ResponseEntity<>(this.listService.addList(dto, userPrincipal.getUserId()), HttpStatus.CREATED);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/find/{listId}")
    public ResponseEntity<?> findList(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable long listId){
        try {
            return ResponseEntity.ok(this.listService.findList(userPrincipal.getUserId(), listId));
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllLists(@AuthenticationPrincipal UserPrincipal userPrincipal){
        try {
            return ResponseEntity.ok(this.listService.getUserLists(userPrincipal.getUserId()));
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/update/{listId}")
    public ResponseEntity<?> updateList(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                        @PathVariable long listId, @RequestBody @Valid UpdateListDto dto){
        try {
            return ResponseEntity.ok(this.listService.updateList(userPrincipal.getUserId(), listId, dto));
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{listId}")
    public ResponseEntity<?> deleteList(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable long listId){
        try{
            this.listService.deleteList(userPrincipal.getUserId(), listId);
            return ResponseEntity.ok().body("Deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
