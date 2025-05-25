package users.rishik.toDoList.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import users.rishik.toDoList.enums.Priority;
import users.rishik.toDoList.enums.Status;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "date_created")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "due_date", nullable = false)
    private LocalDateTime dueDate;

    @Column(name = "priority", nullable = false)
    private Priority priority;

    @Column(name = "status", nullable = false)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "list_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TaskList taskList;

}
