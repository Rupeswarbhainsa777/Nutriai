package com.code.Nutriai.service;

import com.code.Nutriai.dto.LoginRequest;
import com.code.Nutriai.model.User;
import com.code.Nutriai.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public User registration(User user){
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser.isPresent()) {
            return null; // email already exists
        }

        return userRepository.save(user);
    }

    public User login(LoginRequest request) {
        Optional<User> user = userRepository.findByEmail(request.getEmail());

        if (user.isPresent() &&
                user.get().getPassword().equals(request.getPassword())) {

            return user.get();
        }

        return null;
    }
}
