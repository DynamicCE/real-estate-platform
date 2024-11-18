package com.erkan.user_service.service;

import com.erkan.user_service.dto.UserRegistrationRequest;
import com.erkan.user_service.model.User;

public interface UserService {
    User registerUser(UserRegistrationRequest request);

    User getUserByEmail(String email);

    void enableUser(String email);
}
