package fr.mickaelbaron.helloworldpartialwebsocket;

import java.io.IOException;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
@ServerEndpoint(value = "/hellomessagemax")
public class HelloworldMessageMaxEndpoint {

	@OnOpen
	public void onOpen(Session session) {
		System.out.println("HelloworldMessageMaxEndpoint.onOpen()");

		System.out.println("Connected from: " + session.getRequestURI());
	}

	@OnMessage(maxMessageSize = 500)
	public void onTextMessage(String message, Session session) throws IOException {
		System.out.println("HelloworldMessageMaxEndpoint.onTextMessage()");

		System.out.println("Text length " + message.length());

		session.getBasicRemote().sendText(System.currentTimeMillis() + ":" + message.length());
	}

	@OnMessage(maxMessageSize = 500)
	public void onBinaryMessage(byte[] message, Session session) throws IOException {
		System.out.println("HelloworldMessageMaxEndpoint.onBinaryMessage()");

		System.out.println("Binary length " + message.length);

		session.getBasicRemote().sendText(System.currentTimeMillis() + ":" + message.length);
	}

	@OnError
	public void onError(Session session, Throwable exception) {
		System.out.println("HelloworldMessageMaxEndpoint.onError()");
		
		System.out.println(exception.getMessage());
	}
	
	@OnClose
	public void onClose(Session session, CloseReason reason) {
		System.out.println("HelloworldMessageMaxEndpoint.onClose()");

		System.out.println(reason.getReasonPhrase());
	}
}
