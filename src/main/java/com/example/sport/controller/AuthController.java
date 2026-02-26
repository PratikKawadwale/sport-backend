package com.example.sport.controller;

import com.example.sport.model.User;
import com.example.sport.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public User register(@RequestBody User user){
        return userRepository.save(user);
    }

    @PostMapping("/login")
    public User login(@RequestBody User user){
        User u = userRepository.findByEmail(user.getEmail());
        if(u != null && u.getPassword().equals(user.getPassword())){
            return u;
        }
        return null;
    }
}