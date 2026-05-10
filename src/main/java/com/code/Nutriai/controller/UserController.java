package com.code.Nutriai.controller;


import com.code.Nutriai.model.User;
import com.code.Nutriai.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<User>> users(){

        return  userService.allUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> user(@PathVariable long id){
        return userService.user(id);

    }
}
