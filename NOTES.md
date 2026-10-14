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