package com.example.recrutement_microservice.repository;

import com.example.recrutement_microservice.model.Chercheur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChercheurRepository extends JpaRepository<Chercheur, Long> {
    List<Chercheur> findByOfferId(Long offerid);
}
