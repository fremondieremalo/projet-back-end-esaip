package org.sebsy.compta.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table (name = "article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ref;
    @Column(nullable = false, length = 13, unique = true, updatable = false)
    public BigDecimal prix;
    @Column(nullable = false, precision = 7, scale = 2)
    public char designation;
    public boolean actif;
    public BigDecimal prixTtc;
}
