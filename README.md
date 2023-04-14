# applicationRest-user
ApplicationRest-User est une application RESTful qui permet de gérer des utilisateurs. Elle est développée en utilisant le framework Spring Boot et utilise plusieurs technologies.
#Lancement de l'application
- Cloner le dépôt avec : git clone https://github.com/souhailEL99/applicationRest-user.git
- Build avec : mvn clean instaLL -U
- Lancer le serveur : mvn spring-boot:run
- Voir la santé du serveur : http://localhost:8080/actuator/health
- Ouvrir la console h2 : http://localhost:8080/h2-console/
- Générer la javadoc : mvn javadoc:javadoc

#Aspects technologiques utilisés
- L'application utilise plusieurs technologies, notamment :

- H2 : Une base de données relationnelle en mémoire utilisée pour stocker les données des utilisateurs.
- AOP : Un paradigme de programmation permettant de définir des aspects transversaux à l'application, tels que la gestion des erreurs et des exceptions.
- @Trace : Une annotation utilisée pour tracer l'exécution des méthodes de l'application selon notre choix et notre besoin. (ici : temps d'execution + valeur retournée)
- Eself4g : Une bibliothèque de logging utilisée pour enregistrer les événements de l'application.
- MapStruct : Une bibliothèque de mappage d'objets Java utilisée pour convertir les entités de la base de données en objets de la couche de service à l'aide       d'annotations. c'est un générateur/mappeur de code basé sur des annotations qui simplifie grandement les implémentations de mappage des Java Beans.
- Failsafe : Une bibliothèque de gestion des erreurs utilisée pour gérer les erreurs de communication avec des services externes, elle sert à partitionner les tests dans les applications Java basées sur Maven afin d'accélérer l'exécution des tests et des builds.

