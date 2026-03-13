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

    // Delete Team API
    @DeleteMapping("/teams/{id}")
    public ResponseEntity<?> deleteTeam(@PathVariable String id) {
        teamRepository.deleteById(id);
        return ResponseEntity.ok("Team deleted successfully");
    }

    // Add Player to Team API
    @PostMapping("/teams/{teamId}/addPlayer")
    public ResponseEntity<?> addPlayer(@PathVariable String teamId, @RequestBody com.example.sport.model.Player player) {
        java.util.Optional<Team> teamOptional = teamRepository.findById(teamId);
        if (teamOptional.isPresent()) {
            Team team = teamOptional.get();
            if (team.getPlayers() == null) {
                team.setPlayers(new java.util.ArrayList<>());
            }
            team.getPlayers().add(player);
            teamRepository.save(team);
            return ResponseEntity.ok(team);
        } else {
            return ResponseEntity.status(404).body("Team not found");
        }
    }
}