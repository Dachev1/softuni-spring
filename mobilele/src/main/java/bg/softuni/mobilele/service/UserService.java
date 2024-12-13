package bg.softuni.mobilele.service;

import bg.softuni.mobilele.model.dtos.user.LoginDTO;
import bg.softuni.mobilele.model.dtos.user.RegistrationDTO;

public interface UserService {
    void registerUser(RegistrationDTO registrationDTO);

    boolean validateUser(LoginDTO loginDTO);
}
