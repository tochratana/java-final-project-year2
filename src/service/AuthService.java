package service;

import dto.UserLoginDTO;
import dto.UserRegistrationDTO;
import model.User;

public interface AuthService {
    User register(UserRegistrationDTO userRegistrationDTO);
    User login(UserLoginDTO userLoginDTO);
}