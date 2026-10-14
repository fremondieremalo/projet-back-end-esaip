package org.sebsy.compta.dao.jdbc;

import org.sebsy.compta.dao.FournisseurDao;
import org.sebsy.compta.domain.Fournisseur;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

public class FournisseurDaoJdbc implements FournisseurDao {

    @Override
    public Optional<Fournisseur> parId(Long id) {
        String sql = "SELECT * FROM Fournisseur WHERE id = ?";
        return Tx.executer(connexion -> {
            try (PreparedStatement ps = connexion.prepareStatement(sql)) {
                ps.setLong(1, id);
                ps.executeQuery();
            }
            return Optional.empty();
        });
    }

    @Override
    public Optional<Fournisseur> parNom(String nom) {
        String sql = "SELECT * FROM Fournisseur WHERE nom = ?";
        return Tx.executer(connexion -> {
            try (PreparedStatement ps = connexion.prepareStatement(sql)) {
                ps.setString(1, nom);
                ps.executeQuery();
            }
            return Optional.empty();
        });
    }

    @Override
    public List<Fournisseur> parVille(String ville) {
        String sql = "SELECT * FROM Fournisseur WHERE ville = ?";
        return Tx.executer(connexion -> {
            try (PreparedStatement ps = connexion.prepareStatement(sql)) {
                ps.setString(1, ville);
                ps.executeQuery();
            }
            return List.of();
        });
    }

    @Override
    public List<Fournisseur> toutes() {
        String sql = "SELECT * FROM Fournisseur";
        return Tx.executer(connexion -> {
            try (PreparedStatement ps = connexion.prepareStatement(sql)) {
                ps.executeQuery();
            }
            return List.of();
        });
    }

    @Override
    public void creer(Fournisseur fournisseur) {
        String sql = "INSERT INTO Fournisseur (id, nom, ville) VALUES (?, ?, ?)";
        Tx.executer(connexion -> {
            try (PreparedStatement ps = connexion.prepareStatement(sql)) {
                ps.setLong(1, fournisseur.getId());
                ps.setString(2, fournisseur.getNom());
                ps.setString(3, fournisseur.getAdresse() != null ? fournisseur.getAdresse().ville() : null);
                ps.executeUpdate();
            }
            return null;
        });
    }

    @Override
    public void renommer(Long id, String nom) {
        String sql = "UPDATE Fournisseur SET nom = ? WHERE id = ?";
        Tx.executer(connexion -> {
            try (PreparedStatement ps = connexion.prepareStatement(sql)) {
                ps.setString(1, nom);
                ps.setLong(2, id);
                ps.executeUpdate();
            }
            return null;
        });
    }

    @Override
    public void supprimer(Long id) {
        String sql = "DELETE FROM Fournisseur WHERE id = ?";
        Tx.executer(connexion -> {
            try (PreparedStatement ps = connexion.prepareStatement(sql)) {
                ps.setLong(1, id);
                ps.executeUpdate();
            }
            return null;
        });
    }
}