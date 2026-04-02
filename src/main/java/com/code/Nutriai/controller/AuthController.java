package com.code.Nutriai.controller;

import com.code.Nutriai.model.User;
import com.code.Nutriai.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    @Autowired
    private AuthService authService;

    @PostMapping("/reg")
    public ResponseEntity<User> registration(@RequestBody  User user){


        User user1 = authService.registration(user);

        if(user1 == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(user1);

    }



}
