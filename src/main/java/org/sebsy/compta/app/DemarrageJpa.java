package org.sebsy.compta.app;

import org.hibernate.SessionFactory;
import org.sebsy.compta.domain.Adresse;
import org.sebsy.compta.domain.Fournisseur;
import org.sebsy.compta.infra.Jpa;

public class DemarrageJpa {
    public static void main(String[] args) {
        SessionFactory factory = Jpa.factory();

        System.out.println(factory.getClass().getName());

        factory.inTransaction(session -> {
            Fournisseur fournisseur = session.find(Fournisseur.class, 4L);
            if (fournisseur != null) {
                System.out.println(fournisseur.getNom());
                System.out.println("Adresse complète ? " + Adresse.estComplete(fournisseur.getAdresse()));
            }
        });

        Jpa.close();
    }
}