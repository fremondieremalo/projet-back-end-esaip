package org.sebsy.compta.dao.jdbc;

import org.sebsy.compta.dao.PersistenceFailure;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Tx {

    private static Connection getConnexion() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/compta", "app", "app");
    }

    @FunctionalInterface
    public interface SqlWork<T> {
        T execute(Connection connexion) throws SQLException;
    }

    public static <T> T executer(SqlWork<T> travail) {
        return with(travail);
    }

    public static <T> T with(SqlWork<T> travail) {
        try (Connection connexion = getConnexion()) {
            return travail.execute(connexion);
        } catch (SQLException e) {
            throw new PersistenceFailure("Erreur SQL simple : " + e.getMessage());
        }
    }

    public static <T> T call(SqlWork<T> travail) {
        try (Connection connexion = getConnexion()) {
            connexion.setAutoCommit(false);

            try {
                T resultat = travail.execute(connexion);
                connexion.commit();
                return resultat;
            } catch (Exception e) {
                connexion.rollback();
                throw new PersistenceFailure("Erreur transactionnelle (Rollback effectué) : " + e.getMessage());
            }
        } catch (SQLException e) {
            throw new PersistenceFailure("Erreur de connexion : " + e.getMessage());
        }
    }
}