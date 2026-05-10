package com.code.Nutriai.service;


import com.code.Nutriai.model.User;
import com.code.Nutriai.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {


    @Autowired
    private UserRepository repository;


    public ResponseEntity<List<User>> allUsers(){
        List<User> allUser = repository.findAll();

        return ResponseEntity.ok(allUser);
    }


}
