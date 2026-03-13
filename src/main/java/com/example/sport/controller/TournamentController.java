package com.example.sport.controller;

import com.example.sport.model.Tournament;
import com.example.sport.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/tournaments")
@CrossOrigin
public class TournamentController {

    @Autowired
    private TournamentRepository tournamentRepository;

    @PostMapping
    public ResponseEntity<Tournament> createTournament(@RequestBody Tournament tournament) {
        updateStatus(tournament);
        return ResponseEntity.ok(tournamentRepository.save(tournament));
    }

    @GetMapping
    public ResponseEntity<List<Tournament>> getAllTournaments() {
        List<Tournament> tournaments = tournamentRepository.findAll();
        tournaments.forEach(this::updateStatus);
        return ResponseEntity.ok(tournaments);
    }

    private void updateStatus(Tournament tournament) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
            LocalDate start = LocalDate.parse(tournament.getStartDate(), formatter);
            LocalDate end = LocalDate.parse(tournament.getEndDate(), formatter);
            LocalDate now = LocalDate.now();

            if (now.isBefore(start)) {
                tournament.setStatus("Upcoming");
            } else if (now.isAfter(end)) {
                tournament.setStatus("Completed");
            } else {
                tournament.setStatus("Ongoing");
            }
        } catch (Exception e) {
            if (tournament.getStatus() == null) tournament.setStatus("Upcoming");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTournament(@PathVariable String id) {
        tournamentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
