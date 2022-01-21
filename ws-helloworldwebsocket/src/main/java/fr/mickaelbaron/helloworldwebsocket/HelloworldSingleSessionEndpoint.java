package fr.mickaelbaron.helloworldwebsocket;

import java.io.IOException;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
@ServerEndpoint(value = "/hellosinglesession")
public class HelloworldSingleSessionEndpoint {

	@OnOpen
	public void onOpen(Session session) {
		System.out.println("HelloworldSingleSessionEndpoint.onOpen()");
	}

	@OnMessage
	public void onMessage(String message, Session session) throws IOException {
		System.out.println("HelloworldSingleSessionEndpoint.onMessage()");

		session.getBasicRemote().sendText(System.currentTimeMillis() + ":" + message);
	}

	@OnClose
	public void onClose() {
		System.out.println("HelloworldSingleSessionEndpoint.onClose()");
	}
}
