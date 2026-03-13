package com.example.sport.controller;

import com.example.sport.model.Match;
import com.example.sport.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/matches")
@CrossOrigin
public class MatchController {

    @Autowired
    private MatchRepository matchRepository;

    @PostMapping
    public ResponseEntity<Match> createMatch(@RequestBody Match match) {
        updateMatchStatus(match);
        return ResponseEntity.ok(matchRepository.save(match));
    }

    @GetMapping
    public ResponseEntity<List<Match>> getAllMatches() {
        List<Match> matches = matchRepository.findAll();
        matches.forEach(this::updateMatchStatus);
        return ResponseEntity.ok(matches);
    }

    private void updateMatchStatus(Match match) {
        try {
            // Parsing format matches your app's date and time picker format
            // Date: YYYY-MM-DD, Time: HH:mm
            String dateTimeStr = match.getDate() + " " + match.getTime();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d H:m");
            LocalDateTime matchStart = LocalDateTime.parse(dateTimeStr, formatter);
            LocalDateTime now = LocalDateTime.now();

            if (now.isBefore(matchStart)) {
                match.setStatus("Scheduled");
            } else if (now.isAfter(matchStart.plusHours(2))) {
                match.setStatus("Completed");
            } else {
                match.setStatus("Ongoing");
            }
        } catch (Exception e) {
            // Default to Scheduled if parsing fails
            if (match.getStatus() == null)
                match.setStatus("Scheduled");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable String id) {
        matchRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
