# Projet ws-chatwebsocket

Cet exemple implémente un espace de discussion « chat » en utilisant la spécification Jakarta WebSocket (anciennement JSR 356) et l'implémentation Tyrus. L'intérêt de cet exemple est double. D'une part les _URI-template_ sont utilisés pour transmettre des paramètres lors de la phase de connexion. D'autre part deux implémentations différentes d'un WebSocket serveur sont réalisées pour un résultat identique. Ces deux implémentations se basent sur l'utilisation de l'annotation `@ServerEndpoint` et sur l'héritage de la classe `Endpoint`.

Un client HTML/JavaScript a été développé pour implémenter le WebSocket client et afin de pouvoir tester notre développement (répertoire _static/_). Nous montrons également comment déployer les WebSockets serveurs comme une application Java classique par l'intermédiaire du serveur web Grizzly.

## Comment compiler

- À la racine du projet, exécuter la ligne de commande suivante :

```bash
mvn clean package
```

## Comment exécuter

- Toujours depuis la racine du projet, exécuter la ligne de commande suivante :

```bash
java -cp "target/classes:target/dependency/*" fr.mickaelbaron.chatwebsocket.ChatWebSocketLauncher
```

La sortie console attendue :

```bash
mars 17, 2025 3:52:43 PM org.glassfish.grizzly.http.server.NetworkListener start
INFO: Started listener bound to [0.0.0.0:8026]
mars 17, 2025 3:52:43 PM org.glassfish.grizzly.http.server.HttpServer start
INFO: [HttpServer] Started.
mars 17, 2025 3:52:43 PM org.glassfish.tyrus.server.Server start
INFO: WebSocket Registered apps: URLs all start with ws://localhost:8026
mars 17, 2025 3:52:43 PM org.glassfish.tyrus.server.Server start
INFO: WebSocket server started.
Tyrus app started available at ws://localhost:8026/chatwebsocket
Hit enter to stop it...
```

## Tester

Deux WebSockets sont disponibles respectivement à partir de ces URL :

- <ws://localhost:8026/chatwebsocket/chat/{chatroom}/{username}> : permet de se connecter au WebSocket serveur en indiquant un salon et un nom d'utilisatuer (implémentation via l'annotation `@ServerEndpoint` ;
- <ws://localhost:8026/chatwebsocket/chatapi/{chatroom}/{username}> : permet de se connecter au WebSocket serveur en indiquant un salon et un nom d'utilisatuer (implémentation via l'héritage de la classe `Endpoint`.

Pour tester :

- depuis un navigateur web, saisir l'URL suivante : <http://localhost:8026/> ;

- modificer le champ *Location* en prenant l'une des deux URL présentées ci-dessus ;

- appuyer sur le bouton **Connecter** ;

- saisir un texte depuis le champ *Message* ;

- appuyer sur le bouton **Send** ;

- visualiser le résultat sur la zone _Messages_.

**Note:** pour s'apercevoir de l'intérêt des WebSockets, ouvrir plusieurs onglets via l'adresse <http://localhost:8026/>. Cela simulera la présence de plusieurs clients.
