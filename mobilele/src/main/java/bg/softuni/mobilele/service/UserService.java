package bg.softuni.mobilele.service;

import bg.softuni.mobilele.model.dtos.user.LoginDTO;
import bg.softuni.mobilele.model.dtos.user.RegistrationDTO;
import bg.softuni.mobilele.model.entity.User;

public interface UserService {
    void registerUser(RegistrationDTO registrationDTO);

    User validateAndGetUser(LoginDTO loginDTO);
}
