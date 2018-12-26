package fr.mickaelbaron.chatwebsocket;

import java.util.HashMap;
import java.util.Map;

import org.glassfish.tyrus.server.Server;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
public class ChatWebSocketLauncher {

	public static void main(String[] args) {
		final Map<String, Object> serverProperties = new HashMap<>();
		serverProperties.put(Server.STATIC_CONTENT_ROOT, "./static");
		Server server = new Server("localhost", 8026, "/chatwebsocket", serverProperties, ChatEndpoint.class,
				ChatEndpointUsingAPIConfiguration.class);

		try {
			server.start();

			System.out.println("Tyrus app started available at ws://localhost:8026/chatwebsocket"
					+ "\nHit enter to stop it...");

			System.in.read();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			server.stop();
		}
	}
}
