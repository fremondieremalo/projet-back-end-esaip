TP3 :
Question 6 :
new BigDecimal(0.1) -> 0.1000000000000000055511151231257827021181583404541015625
BigDecimal.valueOf(0.1) -> 0.1
new BigDecimal("0.10") -> 0.10


Question 10 :
Lorsque l'on commente la ligne qui n'autorise pas l'autocommit dans la classe Tx, le programme refuse les requêtes. En effet, on ne connait pas toujours l'intention de chaque requête et on ne peut pas commit quelque chose sans vérifications.

Question 11 :
Itération de l'échec et message complet : HikariPool-1 - Connection is not available, request timed out after 2001ms (total=13, active=13, idle=0, waiting=0)
Délai avant échec : 2001ms, nous avions réglé à 2000
Rapport fuite : HikariCP écrit un avertissement au bout de 20 secondes pour leak dans les logs qu'une connexion a été prise mais pas libérée

TP5 :
Question 2 : createEntityManagerFactory retourne une instance de type : EntityManagerFactory, jakarta.persistence apporte la capacité à lier du java a une base de données
J'ai lancé le mvn dependency:tree -Dincludes=jakarta.persistence, voici les lignes importantes selon moi :
[INFO] org.sebsy:demo-data-access:jar:1.0.0-SNAPSHOT
[INFO] \- org.hibernate.orm:hibernate-core:jar:7.4.13.Final:compile
[INFO]    \- jakarta.persistence:jakarta.persistence-api:jar:3.2.0:compile
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  16.703 s
[INFO] Finished at: 2026-10-14T11:10:27+02:00
[INFO] ------------------------------------------------------------------------

Question 4 :
ExceptionInInitializerError: Unable to build Hibernate SessionFactory  [persistence unit: compta]: Schema validation: missing column [codePostal] in table [fournisseur]

Question 5 :
On corrige dans l'entité propriétaire (Fournisseur) car l'objet valeur (Adresse) est fait pour être réutilisable par d'autres entités, elles pourront donc mapper ces données sur des colonnes nommées différemment.

Question 6 :
La compilation fonctionne

Question 7 :
18/10/2026 10h36 -> je pense que hibernate va refuser de démarrer car integer en java est différent de bigint en SQL
résultat : cela a fonctionné : DEBUG org.hibernate.SQL - select f1_0.id,f1_0.rue,f1_0.code_postal,f1_0.ville,f1_0.nom from fournisseur f1_0 where f1_0.id=?
                                Quincaillerie du Maine
                                Adresse complète ? false
                                
avec string a la place de int pour id cela ne fonctionne pas :
[ERROR] Failed to execute goal org.codehaus.mojo:exec-maven-plugin:3.6.4:java (default-cli) on project demo-data-access: An exception occurred while executing the Java class. null: ExceptionInInitializerError: Unable to build Hibernate SessionFactory  [persistence unit: compta]: Schema validation: wrong column type encountered in column [id] in table [article]; found [bigint (Types#BIGINT)], but expecting [varchar(255) (Types#VARCHAR)] -> [Help 1]


validate vérifie la compatibilité exacte des types JDBC, il tolère un type plus petit (Integer vers BIGINT), mais bloque les incompatibilités (String vers BIGINT)

la limite d'un Integer en java est d'environ 2,14 milliards. Si l'ID BIGINT en base de données dépasse cette valeur, l'application Java plantera pour dépassement de capacité

Question 8 :
j'ai changé le nom de la table fournisseur avec une erreur a fournisseurs :
[ERROR] Failed to execute goal org.codehaus.mojo:exec-maven-plugin:3.6.4:java (default-cli) on project demo-data-access: An exception occurred while executing the Java class. null: ExceptionInInitializerError: Unable to build Hibernate SessionFactory  [persistence unit: compta]: Schema validation: missing table [fournisseurs] -> [Help 1]


