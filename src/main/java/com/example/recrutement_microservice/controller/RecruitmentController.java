package com.example.recrutement_microservice.controller;

import java.util.ArrayList;
import java.util.List;

import com.example.recrutement_microservice.exception.ResourceNotFoundException;
import com.example.recrutement_microservice.model.Chercheur;
import com.example.recrutement_microservice.repository.ChercheurRepository;
import com.example.recrutement_microservice.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/v1/offers")
public class RecruitmentController {

    @Autowired
    private ChercheurRepository chercheurRepository;

    @Autowired
    private OfferRepository offerRepository;

    @GetMapping("/{offerId}/chercheurs")
    public ResponseEntity<List<Chercheur>> getAllChercheursByOfferId(@PathVariable(value = "offerId") Long offerId) throws ResourceNotFoundException {
        if (!offerRepository.existsById(offerId)) {
            throw new ResourceNotFoundException("Not found Tutorial with id = " + offerId);
        }

        List<Chercheur> chercheurs = chercheurRepository.findByOfferId(offerId);
        return new ResponseEntity<>(chercheurs, HttpStatus.OK);
    }

    @GetMapping("/chercheurs/{id}")
    public ResponseEntity<Chercheur> getChercheursByOfferId(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        Chercheur chercheur = chercheurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Comment with id = " + id));

        return new ResponseEntity<>(chercheur, HttpStatus.OK);
    }

    @PutMapping("/chercheurs/{id}/{offerId}")
    public ResponseEntity<Chercheur> updateChercheur(@PathVariable("id") long id, @PathVariable("offerId") long offreId, @RequestBody Chercheur chercheurRequest) throws ResourceNotFoundException {
        Chercheur chercheur = chercheurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CommentId " + id + "not found"));
        offerRepository.findById(offreId).map( offre -> {
            chercheur.setOffer(offre);
            return "offer found";
        }).orElseThrow(() -> new ResourceNotFoundException("Not found Offer with id = " + offreId));

        return new ResponseEntity<>(chercheurRepository.save(chercheur), HttpStatus.OK);

    }
}
