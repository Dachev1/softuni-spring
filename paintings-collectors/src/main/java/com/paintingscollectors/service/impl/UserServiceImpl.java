package com.paintingscollectors.service.impl;

import com.paintingscollectors.config.UserSession;
import com.paintingscollectors.model.dto.UserLoginDTO;
import com.paintingscollectors.model.dto.UserRegisterDTO;
import com.paintingscollectors.model.entity.User;
import com.paintingscollectors.repository.UserRepository;
import com.paintingscollectors.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserSession userSession;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, UserSession userSession) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userSession = userSession;
    }

    @Override
    public void register(UserRegisterDTO userData) {
        User user = modelMapper.map(userData, User.class);
        user.setPassword(passwordEncoder.encode(userData.getPassword()));
        userRepository.save(user);
    }

    @Override
    public boolean authenticate(UserLoginDTO userLoginDTO) {
        Optional<User> user = userRepository.findByUsername(userLoginDTO.getUsername());
        if (user.isPresent() && passwordEncoder.matches(userLoginDTO.getPassword(), user.get().getPassword())) {
            userSession.setCurrentUser(user.get());
            return true;
        }
        return false;
    }

    @Override
    public void logout() {
        userSession.clear();
    }

    @Override
    public boolean doesEmailExist(String email) {
        return userRepository.existsByEmail(email);
    }
}
