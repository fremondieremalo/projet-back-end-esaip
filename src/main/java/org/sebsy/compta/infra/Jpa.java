package org.sebsy.compta.infra;

import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernatePersistenceConfiguration;
import org.hibernate.tool.schema.Action;
import org.sebsy.compta.domain.Fournisseur;

public class Jpa {
    private static final SessionFactory FACTORY = new HibernatePersistenceConfiguration("compta")
            .managedClasses(Fournisseur.class)
            .jdbcUrl(Env.required("DB_URL"))
            .jdbcCredentials(Env.required("DB_USER"), Env.required("DB_PASSWORD"))
            .jdbcPoolSize(10)
            .schemaToolingAction(Action.VALIDATE)
            .property("hibernate.generate_statistics", true)
            .createEntityManagerFactory();

    public static SessionFactory factory() {
        return FACTORY;
    }

    public static void close() {
        if (FACTORY != null) FACTORY.close();
    }
}