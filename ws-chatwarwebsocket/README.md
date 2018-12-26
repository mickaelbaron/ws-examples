# Projet ws-chatwarwebsocket

Cet exemple montre comment déployer un WebSocket serveur avec le langage Java, la spécification JSR 356 et l'implémentation Tyrus sur un serveur d'application Tomcat. Plus précisementn nous expliquons comment déployer le fichier war généré avec un conteneur Docker basé sur une image Tomcat.

Un client HTML/JavaScript a été développé pour implémenter le WebSocket client et afin de pouvoir tester notre développement (répertoire _webapp/_).

## Comment compiler

* À la racine du projet, exécuter la ligne de commande suivante :

```bash
mvn clean package
```

## Comment déployer

* Exécuter la ligne de commande suivante pour télécharger l'image Docker correspondant à la version 9 de Tomcat s'exécutant sous un JRE 11

```bash
docker pull tomcat:9-jre11-slim
```

* Exécuter la ligne de commande suivante permettant de créer un conteneur Docker

```bash
docker run --rm --name chatwarwebsocket-tomcat -v $(pwd)/target/chatwarwebsocket.war:/usr/local/tomcat/webapps/chatwarwebsocket.war -it -p 8080:8080 tomcat:9-jre11-slim
```

## Tester

Un WebSocket serveur est disponible à partir de cette URL :

* <ws://localhost:8080/chatwarwebsocket/chat/{chatroom}/{username}> : permet de se connecter au WebSocket serveur en indiquant un salon et un nom d'utilisatuer.

Pour tester :

* depuis un navigateur web, saisir l'URL suivante : <http://localhost:8080/> ;

* modificer le champ _Location_ en prenant l'une des deux URL présentées ci-dessus ;

* appuyer sur le bouton **Connecter** ;

* saisir un texte depuis le champ _Message_ ;

* appuyer sur le bouton **Send** ;

* visualiser le résultat au format JSON sur la zone _Messages_.

**Note:** pour s'apercevoir de l'intérêt des WebSockets, ouvrir plusieurs onglets via l'adresse <http://localhost:8080/>. Cela simulera la présence de plusieurs clients.