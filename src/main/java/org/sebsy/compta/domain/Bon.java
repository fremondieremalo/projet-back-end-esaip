package org.sebsy.compta.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bon")
public class Bon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, updatable = false)
    private int numero;

    @Column(name = "date_cmde", updatable = false)
    private LocalDateTime dateCommande;

    private int delai;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private Statut statut;

    public Bon() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

    public LocalDateTime getDateCommande() { return dateCommande; }
    public void setDateCommande(LocalDateTime dateCommande) { this.dateCommande = dateCommande; }

    public Integer getDelai() { return delai; }
    public void setDelai(Integer delai) { this.delai = delai; }

    public Statut getStatut() { return statut; }
    public void setStatut(Statut statut) { this.statut = statut; }
}