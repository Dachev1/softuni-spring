package bg.softuni.pathfinder.service;

import bg.softuni.pathfinder.dtos.user.UserRegisterDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void registerUser(UserRegisterDTO userRegisterDTO);
}
