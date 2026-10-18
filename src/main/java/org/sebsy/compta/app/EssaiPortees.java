package org.sebsy.compta.app;

import org.hibernate.SessionFactory;
import org.sebsy.compta.domain.Fournisseur;
import org.sebsy.compta.infra.Jpa;

public class EssaiPortees {
    public static void main(String[] args) {
        SessionFactory factory = Jpa.factory();

        factory.getStatistics().clear();
        factory.inTransaction(session -> {
            Fournisseur f1 = session.find(Fournisseur.class, 1L);
            Fournisseur f2 = session.find(Fournisseur.class, 1L);

            System.out.println("== : " + (f1 == f2));
        });
        System.out.println("nb req exec q10 : " + factory.getStatistics().getPrepareStatementCount());

        factory.getStatistics().clear();
        Fournisseur[] fournisseurs = new Fournisseur[2];

        factory.inTransaction(session -> {
            fournisseurs[0] = session.find(Fournisseur.class, 1L);
        });

        factory.inTransaction(session -> {
            fournisseurs[1] = session.find(Fournisseur.class, 1L);
        });

        System.out.println("== : " + (fournisseurs[0] == fournisseurs[1]));
        System.out.println("equals : " + (fournisseurs[0].equals(fournisseurs[1])));
        System.out.println("nb req exec q11 : " + factory.getStatistics().getPrepareStatementCount());

        Jpa.close();
    }
}