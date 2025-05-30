package users.rishik.toDoList.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import users.rishik.toDoList.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String username);
    User findByEmail(String email);
}
