package com.example.sport.controller;

import com.example.sport.model.TournamentRegistration;
import com.example.sport.repository.TournamentRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tournamentRegistration")
@CrossOrigin
public class TournamentRegistrationController {

    @Autowired
    private TournamentRegistrationRepository repository;

    @PostMapping
    public ResponseEntity<TournamentRegistration> register(@RequestBody TournamentRegistration registration) {
        return ResponseEntity.ok(repository.save(registration));
    }

    @GetMapping
    public ResponseEntity<List<TournamentRegistration>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }
}
