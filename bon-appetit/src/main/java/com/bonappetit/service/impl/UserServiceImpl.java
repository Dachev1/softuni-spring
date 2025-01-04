package com.bonappetit.service.impl;

import com.bonappetit.config.UserSession;
import com.bonappetit.dtos.UserLoginDTO;
import com.bonappetit.dtos.UserRegisterDTO;
import com.bonappetit.model.entity.User;
import com.bonappetit.repo.UserRepository;
import com.bonappetit.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserSession userSession;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserSession userSession) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userSession = userSession;
    }

    @Override
    public boolean register(UserRegisterDTO userRegisterDTO) {
        if (userRepository.existsByUsername(userRegisterDTO.getUsername()) ||
                userRepository.existsByEmail(userRegisterDTO.getEmail())) {
            return false;
        }

        if (!userRegisterDTO.getPassword().equals(userRegisterDTO.getConfirmPassword())) {
            return false;
        }

        User user = new User();
        user.setUsername(userRegisterDTO.getUsername());
        user.setEmail(userRegisterDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        userRepository.save(user);

        return true;
    }

    @Override
    public boolean login(UserLoginDTO userLoginDTO) {
        Optional<User> userOptional = userRepository.findByUsername(userLoginDTO.getUsername());

        if (userOptional.isEmpty() || !passwordEncoder.matches(userLoginDTO.getPassword(), userOptional.get().getPassword())) {
            return false;
        }

        User user = userOptional.get();
        userSession.login(user.getId(), user.getUsername());

        return true;
    }
}