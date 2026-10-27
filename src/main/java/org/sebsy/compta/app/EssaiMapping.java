package org.sebsy.compta.app;

import jakarta.persistence.EntityManager;
import org.hibernate.SessionFactory;
import org.sebsy.compta.domain.Article;
import org.sebsy.compta.domain.Bon;
import org.sebsy.compta.infra.Jpa;

import java.math.BigDecimal;

public class EssaiMapping {
    public static void main(String[] args) {
        try (SessionFactory emf = Jpa.factory();
             EntityManager em = emf.createEntityManager()) {

            Article article1 = em.find(Article.class, 1L);
            if (article1 != null) {
                System.out.println("Prix TTC de l'article 1 : " + article1.getPrixTtc());
            } else {
                System.out.println("Article 1 non trouvé dans la base.");
            }

            em.getTransaction().begin();
            try {
//                Article nouvelArticle = new Article(null, "REF001", BigDecimal.valueOf(42.5), "T", true);
//                em.persist(nouvelArticle);
//                em.getTransaction().commit();
//                System.out.println("Nouvel article inséré avec succès.");

                System.out.println("\n liste bon :");
                em.createQuery("from Bon b order by b.numero", Bon.class)
                        .getResultList()
                        .forEach(bon -> System.out.println("Bon n " + bon.getNumero() + " - Statut : " + bon.getStatut() + " - Date : " + bon.getDateCommande()));

            } catch (Exception e) {
                em.getTransaction().rollback();
                System.err.println("Échec de l'insertion. Message d'erreur : " + e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Jpa.close();
        }
    }
}