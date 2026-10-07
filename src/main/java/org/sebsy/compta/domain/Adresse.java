package org.sebsy.compta.domaine;

public class Adresse {

    String rue;
    String codePostal;
    String ville;

    public Adresse(String r, String c, String v) {
        if (v != null) {
            rue = r;
            codePostal = c;
            ville = v;
        } else {
            System.out.println("Ville nulle donc pas possible de créer l'adresse");
        }
    }


}
