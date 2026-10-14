package org.sebsy.compta.dao.jdbc;

import org.sebsy.compta.dao.PersistenceFailure;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

public class BonDaoJdbc {

    public long insererEntete(int numero, LocalDateTime date, long idFournisseur) {
        String sql = "INSERT INTO bon (numero, date_cmde, id_fou) VALUES (?, ?, ?)";
        return Tx.with(connexion -> {
            try (PreparedStatement ps = connexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, numero);
                ps.setObject(2, date);
                ps.setLong(3, idFournisseur);
                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getLong(1);
                    }
                    throw new PersistenceFailure("Aucun ID généré lors de l'insertion de l'en-tête.");
                }
            }
        });
    }

    public void insererLigne(long idBon, long idArticle, int quantite) {
        String sql = "INSERT INTO compo (id_bon, id_art, qte) VALUES (?, ?, ?)";
        Tx.with(connexion -> {
            try (PreparedStatement ps = connexion.prepareStatement(sql)) {
                ps.setLong(1, idBon);
                ps.setLong(2, idArticle);
                ps.setInt(3, quantite);
                ps.executeUpdate();
            }
            return null;
        });
    }

    public long creerAvecLignes(int numero, LocalDateTime date, long idFournisseur, Map<Long, Integer> quantitesParArticle) {
        return Tx.call(connexion -> {
            long idBon = insererEntete(numero, date, idFournisseur);

            for (Map.Entry<Long, Integer> ligne : quantitesParArticle.entrySet()) {
                insererLigne(idBon, ligne.getKey(), ligne.getValue());
            }

            return idBon;
        });
    }

    public Optional<LocalDateTime> dateCommande(long idBon) {
        String sql = "SELECT date_cmde FROM bon WHERE id = ?";
        return Tx.with(connexion -> {
            try (PreparedStatement ps = connexion.prepareStatement(sql)) {
                ps.setLong(1, idBon);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return Optional.ofNullable(rs.getObject("date_cmde", LocalDateTime.class));
                    }
                }
            }
            return Optional.empty();
        });
    }
}