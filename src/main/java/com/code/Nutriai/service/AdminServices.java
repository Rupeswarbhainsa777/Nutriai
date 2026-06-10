package com.code.Nutriai.service;

import com.code.Nutriai.model.User;
import com.code.Nutriai.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServices {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdminServices adminServices;


    public List<User> getUserList(){
        return userRepository.findAll();
    }


}
