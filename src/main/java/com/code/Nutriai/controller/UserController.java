package com.code.Nutriai.controller;


import com.code.Nutriai.model.User;
import com.code.Nutriai.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<User>> users() {

        return userService.allUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> user(@PathVariable Long id) {
        return userService.user(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.update(id, user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @GetMapping("/me")
    public ResponseEntity<User> getCurrent(Authentication authentication) {
        String email = authentication.getName();
        User user = userService.getCurrentUser(email).getBody();
        return ResponseEntity.ok(user);

    }
}
