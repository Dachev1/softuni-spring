package bg.softuni.pathfinder.service.impl;

import bg.softuni.pathfinder.dtos.user.UserRegisterDTO;
import bg.softuni.pathfinder.model.entity.User;
import bg.softuni.pathfinder.repository.UserRepository;
import bg.softuni.pathfinder.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean usernameExists(String username) {
        return this.userRepository.existsByUsername(username);
    }
    @Override
    public boolean emailExists(String email) { // Implementation of emailExists
        return this.userRepository.existsByEmail(email);
    }

    @Override
    public void registerUser(UserRegisterDTO userRegisterDTO) {
        User user = modelMapper.map(userRegisterDTO, User.class);
        this.userRepository.saveAndFlush(user);
    }
}
