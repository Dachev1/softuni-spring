package bg.softuni.pathfinder.service.impl;

import bg.softuni.pathfinder.dtos.user.UserRegisterDTO;
import bg.softuni.pathfinder.model.entity.User;
import bg.softuni.pathfinder.repository.UserRepository;
import bg.softuni.pathfinder.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerUser(UserRegisterDTO userRegisterDTO) {
        if (userRepository.existsByUsername(userRegisterDTO.getUsername())) {
            throw new IllegalArgumentException("Username already exists.");
        }

        if (userRepository.existsByEmail(userRegisterDTO.getEmail())) {
            throw new IllegalArgumentException("Email already exists.");
        }

        User user = modelMapper.map(userRegisterDTO, User.class);
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));

        userRepository.save(user);
        LOGGER.info("User registered successfully: {}", user.getUsername());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRoles().stream()
                        .map(role -> "ROLE_" + role.getName().toUpperCase())
                        .toList()
                        .toArray(new String[0]))
                .build();
    }
}
