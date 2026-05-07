package com.code.Nutriai.service;

import com.code.Nutriai.dto.LoginRequest;
import com.code.Nutriai.model.User;
import com.code.Nutriai.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {


    @Autowired
    private JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Autowired
    private UserRepository userRepository;

    public User registration(User user) {

        // check duplicate email
        if(userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // encode password
        user.setPassword(encoder.encode(user.getPassword()));

        // set user in MealPlans
        if(user.getMealPlans() != null) {
            user.getMealPlans().forEach(mealPlan -> {
                mealPlan.setUser(user);
            });
        }

        // set user in Preference
        if(user.getPreference() != null) {
            user.getPreference().setUser(user);
        }

        return userRepository.save(user);
    }

    public String login(LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
        if(authentication.isAuthenticated()){
            return jwtService.generateToken(request.getEmail());
        }
        return "Fail";


    }
}
