# Projet ws-chatclientwebsocket

Cet exemple montre comment utiliser la spécification JSR 356 et l'implémentation Tyrus pour développer des WebSockets clients avec le langage Java. Pour tester ces WebSockets clients, vous devrez démarrer le WebSocket serveur du projet [ws-chatwebsocket](../ws-chatwebsocket).

Nous montrons également comment déployer les WebSockets clients comme une application Java classique.

## Démarrer le WebSocket serveur du projet _ws-chatwebsocket_

* Se déplacer à la racine du projet _ws-chatwebsocket_

* Compiler le projet

```console
mvn clean package
```

* Exécuter le projet

```console
java -cp "target/classes:target/dependency/*" fr.mickaelbaron.helloworldwebsocket.HelloworldWebSocketLauncher
déc. 23, 2018 7:36:15 PM org.glassfish.grizzly.http.server.NetworkListener start
INFO: Started listener bound to [0.0.0.0:8025]
déc. 23, 2018 7:36:15 PM org.glassfish.grizzly.http.server.HttpServer start
INFO: [HttpServer] Started.
déc. 23, 2018 7:36:15 PM org.glassfish.tyrus.server.Server start
INFO: WebSocket Registered apps: URLs all start with ws://localhost:8025
déc. 23, 2018 7:36:15 PM org.glassfish.tyrus.server.Server start
INFO: WebSocket server started.
Tyrus app started available at ws://localhost:8025/helloworldwebsocket
Hit enter to stop it...
```

## Comment compiler

* À la racine du projet _ws-chatclientwebsocket_, exécuter la ligne de commande suivante :

```console
mvn clean package
```

## Comment exécuter le client via l'annotation `@ClientEndpoint`

* Toujours depuis la racine du projet, exécuter la ligne de commande suivante :

```console
$ java -cp "target/classes:target/dependency/*" fr.mickaelbaron.chatclientwebsocket.ChatClientWebSocketLauncher
ChatClientEndpoint.onOpen()
ChatClientEndpoint.onMessage()
Received message: My First Message (from: mickaelbaron)
```

## Comment exécuter le client via l'API `Endpoint`

* Toujours depuis la racine du projet, exécuter la ligne de commande suivante :

```console
$ java -cp "target/classes:target/dependency/*" fr.mickaelbaron.chatclientwebsocket.ChatClientWebSocketUsingAPILauncher
ChatClientWebSocketUsingAPILauncher.onOpen()
ChatClientWebSocketUsingAPILauncher.onMessage()
Received message: My First Message (from: mickaelbaron)
```
