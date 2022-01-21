package fr.mickaelbaron.helloworldpartialwebsocket;

import java.io.IOException;

import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
@ServerEndpoint(value = "/hellopartial")
public class HelloworldPartialEndpoint {

	@OnOpen
	public void onOpen(Session session) {
		System.out.println("HelloworldPartialEndpoint.onOpen()");

		System.out.println("Connected from: " + session.getRequestURI());
	}

	@OnMessage
	public void onTextMessage(String message, Session session, boolean part) throws IOException {
		System.out.println("HelloworldPartialEndpoint.onTextMessage()");

		System.out.println("Text length " + message.length());

		if (part) {
			System.out.println("Whole message received");
		} else {
			System.out.println("Partial message received");
		}

		session.getBasicRemote().sendText(System.currentTimeMillis() + ":" + message.length());
	}
	
	@OnMessage
	public void onBinaryMessage(byte[] message, Session session, boolean part) throws IOException {
		System.out.println("HelloworldPartialEndpoint.onBinaryMessage()");

		System.out.println("Binary length " + message.length);

		if (part) {
			System.out.println("Whole message received");
		} else {
			System.out.println("Partial message received");
		}

		session.getBasicRemote().sendText(System.currentTimeMillis() + ":" + message.length);
	}
}
