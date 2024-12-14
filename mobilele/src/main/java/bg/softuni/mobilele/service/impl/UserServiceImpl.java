package bg.softuni.mobilele.service.impl;

import bg.softuni.mobilele.model.dtos.user.LoginDTO;
import bg.softuni.mobilele.model.dtos.user.RegistrationDTO;
import bg.softuni.mobilele.model.entity.User;
import bg.softuni.mobilele.repository.UserRepository;
import bg.softuni.mobilele.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerUser(RegistrationDTO registrationDTO) {
        User user = new User();
        user.setUsername(registrationDTO.username());
        user.setFirstName(registrationDTO.firstName());
        user.setLastName(registrationDTO.lastName());
        user.setPassword(passwordEncoder.encode(registrationDTO.password()));
        user.setActive(true);
        user.setCreated(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        userRepository.save(user);
    }

    @Override
    public User validateAndGetUser(LoginDTO loginDTO) {
        return userRepository.findByUsername(loginDTO.username())
                .filter(user -> passwordEncoder.matches(loginDTO.password(), user.getPassword()))
                .orElse(null);
    }
}
