# Enoncé TP : Capture the flag

## Mission 1 
Le but de cette mission est de récupérer un _indice précieux_. 
Cette adresse est stocké dans les données internes de ce [site](https://capture-the-flag-surprised-klipspringer.cfapps.io/).
Heureusement vous avez eu accès au code source de l'application, bien que les infos clés comme le mot de passe de la base de données ne sont pas présentes.

Pour cette mission vous n'avez pas besoin d'outils de hacking ni de connaissance particulière dans ce domaine. 

### Pour lancer l'appli en local
- exécuter la tâche Gradle bootRun et connectez-vous à http://localhost:8745

## Mission 2
La 2ème mission consiste à récupérer l'adresse du drapeau, qui ne peut être consulter qu'en se connectant à la la base de données.
Vous devez utiliser l'indice de la mission 1 pour y parvenir.
 
Pour cette mission vous n'avez pas besoin d'outils de hacking. Cette fois il faut cependant exploiter une vulnérabilité de type XSS pour trouver la faille. 


