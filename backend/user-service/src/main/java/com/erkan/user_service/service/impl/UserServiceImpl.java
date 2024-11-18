package com.erkan.user_service.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.erkan.user_service.dto.UserRegistrationRequest;
import com.erkan.user_service.exception.UserAlreadyExistsException;
import com.erkan.user_service.exception.UserNotFoundException;
import com.erkan.user_service.model.User;
import com.erkan.user_service.repository.UserRepository;
import com.erkan.user_service.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User registerUser(UserRegistrationRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException(
                    "Bu email adresi zaten kayıtlı: " + request.getEmail());
        }

        User user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .enabled(false).build();

        return userRepository.save(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Kullanıcı bulunamadı: " + email));
    }

    @Override
    @Transactional
    public void enableUser(String email) {
        User user = getUserByEmail(email);
        user.setEnabled(true);
        userRepository.save(user);
    }
}
