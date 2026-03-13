package com.example.sport.controller;

import com.example.sport.model.Team;
import com.example.sport.repository.TeamRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;

    // Create Team API
    @PostMapping("/createTeam")
    public ResponseEntity<?> createTeam(@RequestBody Team team) {

        Team savedTeam = teamRepository.save(team);
        return ResponseEntity.ok(savedTeam);
    }

    // Get All Teams API
    @GetMapping("/teams")
    public ResponseEntity<?> getAllTeams() {

        return ResponseEntity.ok(teamRepository.findAll());
    }
}