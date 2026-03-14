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

    @PostMapping("/{id}/status")
    public ResponseEntity<Match> updateMatchStatus(@PathVariable String id, @RequestBody Match matchUpdate) {
        java.util.Optional<Match> matchOptional = matchRepository.findById(id);
        if (matchOptional.isPresent()) {
            Match match = matchOptional.get();
            match.setStatus(matchUpdate.getStatus());
            match.setScoreA(matchUpdate.getScoreA());
            match.setScoreB(matchUpdate.getScoreB());
            match.setWinner(matchUpdate.getWinner());
            return ResponseEntity.ok(matchRepository.save(match));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/update")
    public ResponseEntity<Match> updateMatch(@PathVariable String id, @RequestBody Match matchUpdate) {
        java.util.Optional<Match> matchOptional = matchRepository.findById(id);
        if (matchOptional.isPresent()) {
            Match match = matchOptional.get();
            match.setSport(matchUpdate.getSport());
            match.setTeamA(matchUpdate.getTeamA());
            match.setTeamB(matchUpdate.getTeamB());
            match.setDate(matchUpdate.getDate());
            match.setTime(matchUpdate.getTime());
            match.setVenue(matchUpdate.getVenue());
            match.setStatus(matchUpdate.getStatus());
            match.setScoreA(matchUpdate.getScoreA());
            match.setScoreB(matchUpdate.getScoreB());
            return ResponseEntity.ok(matchRepository.save(match));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private void updateMatchStatus(Match match) {
        if ("Pending".equalsIgnoreCase(match.getStatus()) || "Completed".equalsIgnoreCase(match.getStatus())) {
            return;
        }
        try {
            // Parsing format matches your app's date and time picker format
            // Date: YYYY-MM-DD, Time: HH:mm
            String dateTimeStr = match.getDate() + " " + match.getTime();
            // Try different formats to be safe
            DateTimeFormatter formatter;
            if (match.getDate().split("-")[1].length() == 1 || match.getDate().split("-")[2].length() == 1) {
                 formatter = DateTimeFormatter.ofPattern("yyyy-M-d H:m");
            } else {
                 formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            }
            
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
