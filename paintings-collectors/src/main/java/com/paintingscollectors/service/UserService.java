package com.paintingscollectors.service;

import com.paintingscollectors.model.dto.UserLoginDTO;
import com.paintingscollectors.model.dto.UserRegisterDTO;

public interface UserService {
    void register(UserRegisterDTO userData);

    boolean authenticate(UserLoginDTO userLoginDTO);

    void logout();

    boolean doesEmailExist(String email);
}
