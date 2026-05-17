package com.code.Nutriai.service;


import com.code.Nutriai.model.User;
import com.code.Nutriai.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {


    @Autowired
    private UserRepository repository;


    public ResponseEntity<List<User>> allUsers() {
        List<User> allUser = repository.findAll();

        return ResponseEntity.ok(allUser);
    }

    public ResponseEntity<Optional<User>> user(Long id) {
        Optional<User> user = repository.findById(id);

        return ResponseEntity.ok(user);
    }

    public ResponseEntity<User> update(Long id, User user) {

        User existingUser = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        existingUser.setName(user.getName());
        existingUser.setPassword(user.getPassword());
        existingUser.setGoal(user.getGoal());
        existingUser.setDietaryRestrictions(user.getDietaryRestrictions());
        existingUser.setHeight(user.getHeight());
        existingUser.setWeight(user.getWeight());
        existingUser.setAge(user.getAge());
        existingUser.setMealPlans(user.getMealPlans());

        User update = repository.save(existingUser);

        return ResponseEntity.ok(update);
    }

    public ResponseEntity<String> deleteUser(Long id) {
        User existingUser = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        repository.delete(existingUser);
        return ResponseEntity.ok("User deleted successfully with id: " + id);
    }

    public ResponseEntity<User> getCurrentUser(String email) {
        User user =repository.findByEmail(email);

        return ResponseEntity.ok(user);

    }

}
