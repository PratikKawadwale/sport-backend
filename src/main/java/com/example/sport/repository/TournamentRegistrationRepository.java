package com.example.sport.repository;

import com.example.sport.model.TournamentRegistration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentRegistrationRepository extends MongoRepository<TournamentRegistration, String> {
}
