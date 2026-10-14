package org.sebsy.compta.app;

import org.sebsy.compta.dao.PersistenceFailure;
import org.sebsy.compta.dao.jdbc.BonDaoJdbc;
import org.sebsy.compta.dao.jdbc.Tx;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

public class ScenarioBon {

    public static void main(String[] args) {
        BonDaoJdbc bonDao = new BonDaoJdbc();

        int numeroBonValide = 9013;
        int numeroBonInvalide = 9014;
        long idFournisseur = 1L;

        Map<Long, Integer> lignesValides = new LinkedHashMap<>();
        lignesValides.put(1L, 10);
        lignesValides.put(5L, 20);

        System.out.println("Tentative de création du bon valide n° " + numeroBonValide + "...");
        try {
            bonDao.creerAvecLignes(numeroBonValide, LocalDateTime.now(), idFournisseur, lignesValides);
            System.out.println("-> Succès : Le bon " + numeroBonValide + " a été créé.");
        } catch (PersistenceFailure e) {
            System.err.println("-> Échec inattendu : " + e.getMessage());
        }

        Map<Long, Integer> lignesInvalides = new LinkedHashMap<>();
        lignesInvalides.put(1L, 10);
        lignesInvalides.put(5L, 20);
        lignesInvalides.put(999L, 30);

        System.out.println("\nTentative de création du bon invalide n° " + numeroBonInvalide + "...");
        try {
            bonDao.creerAvecLignes(numeroBonInvalide, LocalDateTime.now(), idFournisseur, lignesInvalides);
        } catch (PersistenceFailure e) {
            System.out.println("-> Échec normal et attendu : " + e.getMessage());
        }

        afficherStatistiques(numeroBonInvalide);

        System.out.println("\nNettoyage de la base de données...");
        supprimerBonParNumero(numeroBonValide);
        supprimerBonParNumero(numeroBonInvalide);
        System.out.println("-> Les bons " + numeroBonValide + " et " + numeroBonInvalide + " ont été supprimés.");
    }

    private static void afficherStatistiques(int numero) {
        Tx.with(connexion -> {
            int nbBons = 0;
            int nbLignes = 0;

            String sqlBons = "SELECT COUNT(*) FROM bon WHERE numero = ?";
            try (PreparedStatement ps = connexion.prepareStatement(sqlBons)) {
                ps.setInt(1, numero);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) nbBons = rs.getInt(1);
                }
            }

            String sqlLignes = "SELECT COUNT(l.id_bon) FROM compo l INNER JOIN bon b ON l.id_bon = b.id WHERE b.numero = ?";
            try (PreparedStatement ps = connexion.prepareStatement(sqlLignes)) {
                ps.setInt(1, numero);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) nbLignes = rs.getInt(1);
                }
            }

            System.out.println("\nStatistiques en base pour le numéro " + numero + " :");
            System.out.println("- Nombre d'en-têtes de bon : " + nbBons);
            System.out.println("- Nombre de lignes de bon  : " + nbLignes);
            return null;
        });
    }

    private static void supprimerBonParNumero(int numero) {
        Tx.with(connexion -> {
            String sqlDeleteLignes = "DELETE FROM compo WHERE id_bon IN (SELECT id FROM bon WHERE numero = ?)";
            try (PreparedStatement ps = connexion.prepareStatement(sqlDeleteLignes)) {
                ps.setInt(1, numero);
                ps.executeUpdate();
            }

            String sqlDeleteBon = "DELETE FROM bon WHERE numero = ?";
            try (PreparedStatement ps = connexion.prepareStatement(sqlDeleteBon)) {
                ps.setInt(1, numero);
                ps.executeUpdate();
            }
            return null;
        });
    }
}