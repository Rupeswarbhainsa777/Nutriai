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


    public ResponseEntity<List<User>> allUsers(){
        List<User> allUser = repository.findAll();

        return ResponseEntity.ok(allUser);
    }

    public ResponseEntity<Optional<User>> user(long id){
        Optional<User> user = repository.findById(id);

        return ResponseEntity.ok(user);
    }


}
