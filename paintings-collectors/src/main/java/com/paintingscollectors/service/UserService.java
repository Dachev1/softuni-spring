package com.paintingscollectors.service;

import com.paintingscollectors.model.dto.UserRegisterDTO;

public interface UserService {
    void register(UserRegisterDTO userData);
}
