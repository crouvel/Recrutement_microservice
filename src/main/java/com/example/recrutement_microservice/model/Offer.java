package com.example.recrutement_microservice.model;



import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "offer")
public class Offer {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NotBlank
    private String title;

    @Column(nullable = true)
    private String description;

    @NotBlank
    private Date debut;

    @NotBlank
    private Date fin;

    @NotBlank
    private String city;

    @NotBlank
    private int nbre_postes;


    private int income;

    @NotBlank
    private String advantage;

    //private Long employeur_id;

    /*public Long getEmployeur_id() {
        return employeur_id;
    }

    public void setEmployeur_id(Long employeur_id) {
        this.employeur_id = employeur_id;
    }*/

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "employeur_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Employeur employeur;

    /*@ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "offer_chercheur",
            joinColumns = { @JoinColumn(name = "offer_id") },
            inverseJoinColumns = { @JoinColumn(name = "chercheur_id") })
    private Set<Chercheur> chercheur = new HashSet<>();*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDebut() {
        return debut;
    }

    public void setDebut(Date debut) {
        this.debut = debut;
    }

    public Date getFin() {
        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getNbre_postes() {
        return nbre_postes;
    }

    public void setNbre_postes(int nbre_postes) {
        this.nbre_postes = nbre_postes;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public String getAdvantage() {
        return advantage;
    }

    public void setAdvantage(String advantage) {
        this.advantage = advantage;
    }

    public Employeur getEmployeur() {
        return employeur;
    }

    public void setEmployeur(Employeur employeur) {
        this.employeur = employeur;
    }

    public Offer(Long id, String title, String description, Date debut, Date fin, String city, int nbre_postes, int income, String advantage, Employeur employeur) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.debut = debut;
        this.fin = fin;
        this.city = city;
        this.nbre_postes = nbre_postes;
        this.income = income;
        this.advantage = advantage;
        this.employeur = employeur;
    }

    /*public void addChercheur(Chercheur chercheur) {
        this.chercheur.add(chercheur);
        chercheur.getOffers().add(this);
    }

    public void removeChercheur(long chercheurId) {
        Chercheur chercheur = this.chercheur.stream().filter(t -> t.getId() == chercheurId).findFirst().orElse(null);
        if (chercheur != null) {
            this.chercheur.remove(chercheur);
            chercheur.getOffers().remove(this);
        }
    }*/
    public Offer(){}

}




