# Projet ws-chatjavafxclientwebsocket

Cet exemple montre comment utiliser la spécification Jakarta WebSocket (anciennement JSR 356) et l'implémentation Tyrus pour développer un WebSocket client avec le langage Java et la bibliothèque graphique OpenJFX. Pour tester ce client, vous devrez démarrer le WebSocket serveur du projet [ws-chatwebsocket](../ws-chatwebsocket).

![Chat JavaFX client](./images/ws-chatjavafxclientwebsocket.png "Chat JavaFX client")

## Démarrer le WebSocket serveur du projet _ws-chatwebsocket_

- Se déplacer à la racine du projet _ws-chatwebsocket_

- Compiler le projet

```bash
mvn clean package
```

- Exécuter le projet

```bash
java -cp "target/classes:target/dependency/*" fr.mickaelbaron.helloworldwebsocket.HelloworldWebSocketLauncher
```

La sortie console attendue :

```bash
mars 17, 2025 4:01:14 PM org.glassfish.grizzly.http.server.NetworkListener start
INFO: Started listener bound to [0.0.0.0:8026]
mars 17, 2025 4:01:14 PM org.glassfish.grizzly.http.server.HttpServer start
INFO: [HttpServer] Started.
mars 17, 2025 4:01:14 PM org.glassfish.tyrus.server.Server start
INFO: WebSocket Registered apps: URLs all start with ws://localhost:8026
mars 17, 2025 4:01:14 PM org.glassfish.tyrus.server.Server start
INFO: WebSocket server started.
Tyrus app started available at ws://localhost:8026/chatwebsocket
Hit enter to stop it...
```

## Comment compiler

- À la racine du projet _ws-chatjavafxclientwebsocket_, exécuter la ligne de commande suivante :

```bash
mvn clean package
```

## Comment exécuter avec Maven

- Toujours depuis la racine du projet, exécuter la ligne de commande suivante :

```bash
mvn exec:java
```

La sortie console attendue :

```bash
[INFO] Scanning for projects...
[INFO]
[INFO] ------------< fr.mickaelbaron:ws-chatjavafxclientwebsocket >------------
[INFO] Building Chat JavaFX Client WebSocket Application 1.0.0-SNAPSHOT
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO]
[INFO] --- exec:1.6.0:java (default-cli) @ ws-chatjavafxclientwebsocket ---
```

## Comment exécuter avec Java

- Toujours depuis la racine du projet, exécuter la ligne de commande suivante :

```bash
java -cp 'target/classes:target/dependency/*' --module-path target/dependency --add-modules=javafx.fxml,javafx.controls fr.mickaelbaron.chatjavafxclientwebsocket.ChatJavaFXClientWebSocketApplication
```

**Note:** pour s'apercevoir de l'intérêt des WebSockets, exécuter plusieurs fois l'application JavaFX. Cela simulera la présence de plusieurs clients.
