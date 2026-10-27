package org.sebsy.compta.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 13, unique = true, updatable = false)
    private String ref;

    @Column(nullable = false, precision = 7, scale = 2)
    private BigDecimal prix;

    private String designation;
    private boolean actif;

    @Transient
    private BigDecimal prixTtc;

    public Article() {}

    public Article(Long id, String ref, BigDecimal prix, String designation, boolean actif) {
        this.id = id;
        this.ref = ref;
        this.prix = prix;
        this.designation = designation;
        this.actif = actif;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public BigDecimal getPrix() { return prix; }
    public void setPrix(BigDecimal prix) { this.prix = prix; }

    public BigDecimal getPrixTtc() {
        if (this.prix != null) {
            return this.prix.multiply(BigDecimal.valueOf(1.20)).setScale(2, RoundingMode.HALF_UP);
        }
        return null;
    }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }

    public boolean isActif() { return actif; }
    public void setActif(boolean actif) { this.actif = actif; }
}