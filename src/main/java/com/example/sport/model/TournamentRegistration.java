package com.example.sport.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "tournament_registrations")
public class TournamentRegistration {
    @Id
    private String id;
    private String tournamentId;
    private String teamId;
    private String tournamentName;
    private String teamName;
    private String registrationDate;
}
