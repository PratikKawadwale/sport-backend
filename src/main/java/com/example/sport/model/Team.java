package com.example.sport.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "teams")
public class Team {

    @Id
    private String id;

    private String teamName;

    private String sport;

    private String coachName;
}