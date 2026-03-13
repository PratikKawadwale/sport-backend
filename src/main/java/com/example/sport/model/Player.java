package com.example.sport.model;

import lombok.Data;

@Data
public class Player {
    private String playerName;
    private String position;
    private String jerseyNumber;
    private String idProofImage; // Store as Base64 for simplicity in this demo
}
