package org.sebsy.compta.app;

import org.sebsy.compta.infra.Db;
import java.sql.Connection;
import java.sql.SQLException;

public class Fuite {
    public static void main(String[] args) {
        System.out.println("Début de la simulation de fuite de connexions...");

        for (int i = 1; i <= 200; i++) {
            try {
                // On emprunte une connexion sans jamais la fermer (pas de try-with-resources)
                Connection connexion = Db.getDataSource().getConnection();
            } catch (SQLException e) {
                System.err.println("Échec à l'itération " + i + " : " + e.getMessage());
                break;
            }
        }

        System.out.println("Boucle terminée. Attente de 25 secondes...");
        try {
            Thread.sleep(25000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Fin du programme.");
    }
}