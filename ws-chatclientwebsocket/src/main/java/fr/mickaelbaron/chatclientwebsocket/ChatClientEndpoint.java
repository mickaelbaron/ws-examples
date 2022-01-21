package fr.mickaelbaron.chatclientwebsocket;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import jakarta.websocket.ClientEndpoint;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
@ClientEndpoint
public class ChatClientEndpoint {

	private CountDownLatch countDownLatch;

	public ChatClientEndpoint(CountDownLatch pCountDownLatch) {
		this.countDownLatch = pCountDownLatch;
	}

	@OnOpen
	public void onOpen(Session session) throws IOException {
		System.out.println("ChatClientEndpoint.onOpen()");
	}

	@OnMessage
	public void onMessage(String message) {
		System.out.println("ChatClientEndpoint.onMessage()");

		System.out.println("Received message: " + message);
		countDownLatch.countDown();
	}
}
