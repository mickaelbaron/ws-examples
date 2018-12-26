package fr.mickaelbaron.chatclientwebsocket;

import java.net.URI;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.websocket.Session;

import org.glassfish.tyrus.client.ClientManager;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
public class ChatClientWebSocketLauncher {

	private static CountDownLatch messageLatch = new CountDownLatch(1);

	public static void main(String[] args) {
		try {
			ClientManager client = ClientManager.createClient();
			Session currentSession = client.connectToServer(new ChatClientEndpoint(messageLatch),
					new URI("ws://localhost:8026/chatwebsocket/chat/java/mickaelbaron"));
			currentSession.getBasicRemote().sendText("My First Message");
			messageLatch.await(2, TimeUnit.SECONDS);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
