package org.sebsy.compta.dao;

import org.sebsy.compta.domain.Adresse;
import org.sebsy.compta.domain.Fournisseur;
import java.util.Optional;
import java.util.List;

public interface FournisseurDao {

    Optional<Fournisseur> parId(Long id);
    Optional<Fournisseur> parNom(String nom);

    List<Fournisseur> parVille(String ville);
    List<Fournisseur> toutes();

    void creer(Fournisseur fournisseur);
    void renommer(Long id, String nom);
    void supprimer(Long id);
}