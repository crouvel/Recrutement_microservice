package com.example.recrutement_microservice.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "employeur")
public class Employeur {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    //...

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @NotBlank
    private String entreprise;

    @NotBlank
    private String adresse;

    private String description;


    @Column(nullable = true)
    private int note;

    @NotBlank
    private String activite;

    public Employeur(){}

    public Employeur(Long id, User user, String nomEntreprise, String adresse, String description, int note, String activite) {
        this.id = id;
        this.user = user;
        this.entreprise = nomEntreprise;
        this.adresse = adresse;
        this.description = description;
        this.note = note;
        this.activite = activite;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(String entreprise) {
        this.entreprise = entreprise;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public String getActivite() {
        return activite;
    }

    public void setActivite(String activite) {
        this.activite = activite;
    }
}

