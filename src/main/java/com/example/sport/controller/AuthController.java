package com.example.sport.controller;

import com.example.sport.model.User;
import com.example.sport.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // Register API
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {

        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    // Login API
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {

        User u = userRepository.findByEmailAndPasswordAndRole(
                user.getEmail(),
                user.getPassword(),
                user.getRole()
        );

        if (u != null) {
            return ResponseEntity.ok(u);   // Login Success
        }

        return ResponseEntity.status(401).body("Invalid email, password or role");
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") String id, @RequestBody User user) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setName(user.getName());
            existingUser.setPhone(user.getPhone());
            existingUser.setDob(user.getDob());
            existingUser.setDepartment(user.getDepartment());
            existingUser.setAddress(user.getAddress());
            existingUser.setBloodGroup(user.getBloodGroup());
            // Role and Email usually stay same
            User updated = userRepository.save(existingUser);
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/users")
    public ResponseEntity<java.util.List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }
}