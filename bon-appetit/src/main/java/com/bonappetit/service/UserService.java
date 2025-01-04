package com.bonappetit.service;

import com.bonappetit.dtos.UserLoginDTO;
import com.bonappetit.dtos.UserRegisterDTO;

public interface UserService {
    boolean register(UserRegisterDTO userRegisterDTO);
    boolean login(UserLoginDTO userLoginDTO);
}
