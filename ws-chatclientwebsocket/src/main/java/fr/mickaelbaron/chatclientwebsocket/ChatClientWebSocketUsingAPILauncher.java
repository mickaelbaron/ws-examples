package fr.mickaelbaron.chatclientwebsocket;

import java.net.URI;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

import org.glassfish.tyrus.client.ClientManager;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
public class ChatClientWebSocketUsingAPILauncher {

	private static CountDownLatch messageLatch;

	public static void main(String[] args) {
		try {
			messageLatch = new CountDownLatch(1);

			final ClientEndpointConfig cec = ClientEndpointConfig.Builder.create().build();
			ClientManager client = ClientManager.createClient();
			Session currentSession = client.connectToServer(new Endpoint() {
				@Override
				public void onOpen(Session session, EndpointConfig config) {
					System.out.println("ChatClientWebSocketUsingAPILauncher.onOpen()");
					session.addMessageHandler(new MessageHandler.Whole<String>() {

						@Override
						public void onMessage(String message) {
							System.out.println("ChatClientWebSocketUsingAPILauncher.onMessage()");

							System.out.println("Received message: " + message);
							messageLatch.countDown();
						}
					});
				}
			}, cec, new URI("ws://localhost:8026/chatwebsocket/chat/java/mickaelbaron"));
			currentSession.getBasicRemote().sendText("My First Message");
			
			messageLatch.await(100, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
