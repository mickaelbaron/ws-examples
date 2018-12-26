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
@ServerEndpoint(value = "/hellosinglereturn")
public class HelloworldSingleReturnEndpoint {

	@OnOpen
	public void onOpen(Session session) {
		System.out.println("HelloworldSingleReturnEndpoint.onOpen()");
	}

	@OnMessage
	public String onMessage(String message, Session session) throws IOException {
		System.out.println("HelloworldSingleReturnEndpoint.onMessage()");

		return System.currentTimeMillis() + ":" + message;
	}

	@OnClose
	public void onClose() {
		System.out.println("HelloworldSingleReturnEndpoint.onClose()");
	}
}
