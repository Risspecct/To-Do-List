package users.rishik.toDoList.Services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import users.rishik.toDoList.Repositories.UserRepository;
import users.rishik.toDoList.Security.UserPrincipal;
import users.rishik.toDoList.entities.User;

@Service
public class MyUserDetailsService  implements UserDetailsService {

    private final UserRepository userRepository;

    MyUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.userRepository.findByEmail(email);
        if (user == null){
            System.out.println("User not found");
            throw new UsernameNotFoundException("No user found with email: " + email);
        }
        return new UserPrincipal(user);
    }
}
