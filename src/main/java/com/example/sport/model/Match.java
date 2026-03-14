package com.example.sport.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "matches")
public class Match {
    @Id
    private String id;
    private String sport;
    private String teamA;
    private String teamB;
    private String date;
    private String time;
    private String venue;
    private String status; // Scheduled, Ongoing, Completed
    private int scoreA;
    private int scoreB;
    private String winner;
}
