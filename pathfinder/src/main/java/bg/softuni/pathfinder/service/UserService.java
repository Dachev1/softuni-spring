package bg.softuni.pathfinder.service;

import bg.softuni.pathfinder.dtos.user.UserRegisterDTO;

public interface UserService {
    boolean usernameExists(String username);

    boolean emailExists(String email); // New method

    void registerUser(UserRegisterDTO userRegisterDTO);
}
