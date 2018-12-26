package fr.mickaelbaron.helloworldwebsocket;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
@ServerEndpoint(value = "/hello")
public class HelloworldEndpoint {

	@OnOpen
	public void onOpen(Session session) {
		System.out.println("HelloworldEndpoint.onOpen()");
	}

	@OnMessage
	public void onMessage(String message, Session session) throws IOException {
		System.out.println("HelloworldEndpoint.onMessage()");

		for (Session currentSession : session.getOpenSessions()) {
			currentSession.getBasicRemote().sendText(System.currentTimeMillis() + ":" + message);
		}
	}

	@OnClose
	public void onClose() {
		System.out.println("HelloworldEndpoint.onClose()");
	}
}
