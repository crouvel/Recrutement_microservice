package com.example.recrutement_microservice.repository;

import com.example.recrutement_microservice.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {

}
