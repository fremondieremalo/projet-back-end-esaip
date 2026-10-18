package org.sebsy.compta.domain;

import jakarta.persistence.*;

import java.util.Objects;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="fournisseur")
public class Fournisseur {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(nullable = false, length = 60, unique = true)
    private String nom;
    @Embedded
    @AttributeOverride(name = "codePostal", column = @Column(name = "code_postal"))
    private Adresse adresse;

    protected Fournisseur() {
    }

    public Fournisseur(Long id, String nom, Adresse adresse) {
        this.id = id;
        this.nom = nom;
        this.adresse = adresse;
    }

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fournisseur that = (Fournisseur) o;
        return Objects.equals(nom, that.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom);
    }
}