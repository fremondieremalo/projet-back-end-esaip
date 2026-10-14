package org.sebsy.compta.infra;

import jakarta.persistence.EntityManager;
import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernatePersistenceConfiguration;
import org.hibernate.tool.schema.Action;
import org.sebsy.compta.domain.Fournisseur;

public class Jpa {

    public static void Jpa() {
        SessionFactory emf = new HibernatePersistenceConfiguration("compta")
                .managedClasses(Fournisseur.class)
    // pas de scan en Java SE
                .jdbcUrl(Env.required("DB_URL"))
                .jdbcCredentials(Env.required("DB_USER"), Env.required("DB_PASSWORD"))
                .jdbcPoolSize(10)
    // HikariCP, par délégation
                .schemaToolingAction(Action.VALIDATE)
    // compare, ne modifie rien
                .property("hibernate.generate_statistics", true)
                .createEntityManagerFactory();
        emf.close();
    }
}
