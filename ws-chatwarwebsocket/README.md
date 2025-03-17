# Projet ws-chatwarwebsocket

Cet exemple montre comment déployer un WebSocket serveur avec le langage Java, la spécification JSR 356 et l'implémentation Tyrus sur un serveur d'application Tomcat. Plus précisementn nous expliquons comment déployer le fichier war généré avec un conteneur Docker basé sur une image Tomcat.

Un client HTML/JavaScript a été développé pour implémenter le WebSocket client et afin de pouvoir tester notre développement (répertoire _webapp/_).

## Comment compiler

- À la racine du projet, exécuter la ligne de commande suivante :

```console
mvn clean package
```

## Comment déployer

- Exécuter la ligne de commande suivante pour télécharger l'image Docker correspondant à la version 10 de Tomcat s'exécutant sous un JRE 11 :

```console
docker pull tomcat:jre11-openjdk-slim
```

- Exécuter la ligne de commande suivante permettant de créer un conteneur Docker :

```console
docker run --rm --name chatwarwebsocket-tomcat -v $(pwd)/target/chatwarwebsocket.war:/usr/local/tomcat/webapps/chatwarwebsocket.war -it -p 8080:8080 tomcat:jre11-openjdk-slim
```

## Tester

Un WebSocket serveur est disponible à partir de cette URL :

- <ws://localhost:8080/chatwarwebsocket/chat/{chatroom}/{username}> : permet de se connecter au WebSocket serveur en indiquant un salon et un nom d'utilisateur.

Pour tester :

- depuis un navigateur web, saisir l'URL suivante : <http://localhost:8080/chatwarwebsocket> ;

- modificer le champ *Location* en prenant l'une des deux URL présentées ci-dessus ;

- appuyer sur le bouton **Connecter** ;

- saisir un texte depuis le champ *Message* ;

- appuyer sur le bouton **Send** ;

- visualiser le résultat au format JSON sur la zone _Messages_.

**Note:** pour s'apercevoir de l'intérêt des WebSockets, ouvrir plusieurs onglets via l'adresse <http://localhost:8080/chatwarwebsocket>. Cela simulera la présence de plusieurs clients.
