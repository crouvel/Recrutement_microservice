package com.example.recrutement_microservice.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "employe")
public class Employe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "chercheur_id", referencedColumnName = "id")
    private Chercheur chercheur;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "employeur_id", referencedColumnName = "id")
    private Employeur employeur;

    public Employeur getEmployeur() {
        return employeur;
    }

    public void setEmployeur(Employeur employeur) {
        this.employeur = employeur;
    }

    @NotBlank
    private String nom;

    @NotBlank
    private String prenom;

    @NotBlank
    private String ville;

    private Date naissance;

    @Column(nullable= true)
    private int note;

    public Employe(){

    }

    public Employe(Long id, Chercheur chercheur, String nom, String prenom, String ville, Date dateDeNaissance, int note) {
        this.id = id;
        this.chercheur = chercheur;
        this.nom = nom;
        this.prenom = prenom;
        this.ville = ville;
        this.naissance = dateDeNaissance;
        this.note = note;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Chercheur getChercheur() {
        return chercheur;
    }

    public void setChercheur(Chercheur chercheur) {
        this.chercheur = chercheur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public Date getNaissance() {
        return naissance;
    }

    public void setNaissance(Date dateDeNaissance) {
        this.naissance = dateDeNaissance;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }
}
