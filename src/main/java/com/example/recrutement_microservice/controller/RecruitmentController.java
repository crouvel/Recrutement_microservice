package com.example.recrutement_microservice.controller;

import java.util.ArrayList;
import java.util.List;

import com.example.recrutement_microservice.Producer.RecruitmentProducer;
import com.example.recrutement_microservice.exception.ResourceNotFoundException;
import com.example.recrutement_microservice.model.Chercheur;
import com.example.recrutement_microservice.model.Employe;
import com.example.recrutement_microservice.model.Employeur;
import com.example.recrutement_microservice.model.Offer;
import com.example.recrutement_microservice.repository.ChercheurRepository;
import com.example.recrutement_microservice.repository.EmployeRepository;
import com.example.recrutement_microservice.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/v1/recruit")
public class RecruitmentController {

    private RecruitmentProducer recruitmentProducer;
    @Autowired
    private ChercheurRepository chercheurRepository;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private EmployeRepository employeRepository;

    @GetMapping("/employes")
    public List<Employe> getAllEmployes() {
        return employeRepository.findAll();
    }

    @GetMapping("/employes/{id}")
    public ResponseEntity<Employe> getEmployeById(@PathVariable(value = "id") Long employeId)
            throws ResourceNotFoundException {
        Employe employe = employeRepository.findById(employeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employe not found for this id :: " + employeId));
        return ResponseEntity.ok().body(employe);
    }
    @GetMapping("/{offerId}/chercheurs")
    public ResponseEntity<List<Chercheur>> getAllChercheursByOfferId(@PathVariable(value = "offerId") Long offerId) throws ResourceNotFoundException {
        if (!offerRepository.existsById(offerId)) {
            throw new ResourceNotFoundException("Not found Tutorial with id = " + offerId);
        }

        List<Chercheur> chercheurs = chercheurRepository.findByOfferId(offerId);
        return new ResponseEntity<>(chercheurs, HttpStatus.OK);
    }


    public String notifyApplication(@RequestBody Chercheur chercheur) throws InterruptedException {
        recruitmentProducer.sendMessage("Un nouveau chercheur candidat à une offre : " +chercheur.getPrenom() + " "+ chercheur.getNom() +".");
        return "Nouveau chercheur ...";
    }

    public String notifyDeclined(@RequestBody Chercheur chercheur) throws InterruptedException {
        recruitmentProducer.sendMessage("Le chercheur " +chercheur.getPrenom() + " "+ chercheur.getNom() +" n'est plus candidat");
        return "Chercheur décliné ...";
    }

    public String notifyEmploye(@RequestBody Employe employe) throws InterruptedException {
        recruitmentProducer.sendMessage("Ce candidat est désormais employé " +employe.getPrenom() + " "+ employe.getNom() +" à " + employe.getEmployeur().getEntreprise());
        return "Nouvel employé ...";
    }

    public String notifyOfferSupressed(@RequestBody Offer offer) throws InterruptedException {
        recruitmentProducer.sendMessage("L'offre à l'intitulé " +offer.getTitle().toUpperCase() + " n'est plus disponible." );
        return "Offre supprimée ...";
    }

    public RecruitmentController(RecruitmentProducer recruitmentProducer) {
        this.recruitmentProducer = recruitmentProducer;
    }

    @PutMapping("/chercheurs/{id}/{offerId}")
    public ResponseEntity<Chercheur> updateChercheur(@PathVariable("id") long id, @PathVariable("offerId") long offreId, @RequestBody Chercheur chercheurRequest) throws ResourceNotFoundException, InterruptedException {
        Chercheur chercheur = chercheurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CommentId " + id + "not found"));
        offerRepository.findById(offreId).map( offre -> {
            chercheur.setOffer(offre);
            try {
                notifyApplication(chercheur);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "offer found";
        }).orElseThrow(() -> new ResourceNotFoundException("Not found Offer with id = " + offreId));

        return new ResponseEntity<>(chercheurRepository.save(chercheur), HttpStatus.OK);

    }

    @PutMapping("/chercheurs/{id}/{offerId}/declined")
    public ResponseEntity<Chercheur> updateChercheurDeclined(@PathVariable("id") long id, @PathVariable("offerId") long offreId, @RequestBody Chercheur chercheurRequest) throws ResourceNotFoundException, InterruptedException {
        Chercheur chercheur = chercheurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CommentId " + id + "not found"));
        offerRepository.findById(offreId).map( offre -> {
            chercheur.setOffer(null);
            try {
                notifyDeclined(chercheur);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "offer found";
        }).orElseThrow(() -> new ResourceNotFoundException("Not found Offer with id = " + offreId));

        return new ResponseEntity<>(chercheurRepository.save(chercheur), HttpStatus.OK);

    }

    @PostMapping("/{chercheurId}/{offerId}/employes")
    public ResponseEntity<Employe> createDetails(@PathVariable(value = "chercheurId") Long chercheurId,
                                                 @PathVariable(value = "offerId") Long offerId,
                                                 @RequestBody Employe employeRequest) throws ResourceNotFoundException, InterruptedException {
        Chercheur chercheur = chercheurRepository.findById(chercheurId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Chercheur found with id = " + chercheurId));

        Offer offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Offer found with id = " + offerId));
        Employeur employeur = offer.getEmployeur();
        employeRequest.setChercheur(chercheur);
        employeRequest.setEmployeur(employeur);
        Employe employeToCreate = employeRepository.save(employeRequest);
        notifyEmploye(employeToCreate);
        return new ResponseEntity<>(employeToCreate, HttpStatus.CREATED);
    }

    @DeleteMapping("/employes/{id}")
    public ResponseEntity<HttpStatus> deleteEmploye(@PathVariable("id") long id) throws ResourceNotFoundException {
        Employe employe = employeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Employe found with id = " + id));
        employeRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/offers/{id}")
    public ResponseEntity<Offer> getOfferById(@PathVariable(value = "id") Long offerId)
            throws ResourceNotFoundException {
        Offer offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new ResourceNotFoundException("Offer not found for this id :: " + offerId));
        return ResponseEntity.ok().body(offer);
    }

    @DeleteMapping("/offers/{id}")
    public ResponseEntity<HttpStatus> deleteOffer(@PathVariable("id") long id) throws ResourceNotFoundException, InterruptedException {
        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Offer found with id = " + id));
        offerRepository.deleteById(id);
        notifyOfferSupressed(offer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
