package org.sebsy.compta.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public record Adresse(String rue, String codePostal, String ville) {

    public Adresse {
        if (ville == null) {
            throw new IllegalArgumentException("La ville ne doit pas être nulle");
        }
    }

    public static Adresse ville(String nomVille) {
        return new Adresse(null,null, nomVille);
    }

    public static boolean estComplete(Adresse adr) {
        if (adr.rue == null || adr.codePostal == null || adr.ville == null) {
            return false;
        } else {
            return true;
        }
    }
}
