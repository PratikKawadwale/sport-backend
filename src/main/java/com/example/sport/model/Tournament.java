package com.example.sport.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "tournaments")
public class Tournament {
    @Id
    private String id;
    private String name;
    private String sport;
    private String startDate;
    private String endDate;
    private String venue;
    private int teamCount;
    private String prize;
    private String status; // Upcoming, Ongoing, Completed
}
