package fr.mickaelbaron.chatwebsocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jakarta.websocket.CloseReason;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Endpoint;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.MessageHandler;
import jakarta.websocket.Session;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
public class ChatEndpointUsingAPI extends Endpoint {

	// Key = username
	private static Map<String, Session> allSessions = new HashMap<>();

	// Key = session id
	private static Map<String, String> allUsers = new HashMap<>();

	// Key = session id
	private static Map<String, String> allChatRooms = new HashMap<>();

	@Override
	public void onOpen(Session session, EndpointConfig config) {
		System.out.println("ChatEndpointUsingAPI.onOpen()");

		Map<String, String> pathParameters = session.getPathParameters();
		String chatRoom = pathParameters.get("chatroom");
		String userName = pathParameters.get("username");

		if (allSessions.containsKey(userName)) {
			try {
				session.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			allChatRooms.put(session.getId(), chatRoom);
			allUsers.put(session.getId(), userName);
			allSessions.put(userName, session);

			session.addMessageHandler(String.class, new MessageHandler.Whole<String>() {
				public void onMessage(String message) {
					ChatEndpointUsingAPI.this.broadcast(message + " (from: " + allUsers.get(session.getId()) + ")",
							null, allChatRooms.get(session.getId()));
				}
			});

			this.broadcast(userName + " connected!", session, chatRoom);
		}
	}

	@Override
	public void onClose(Session session, CloseReason closeReason) {
		System.out.println("ChatEndpoint.onClose()");

		String currentUsername = allUsers.get(session.getId());
		allSessions.remove(currentUsername);
		allUsers.remove(session.getId());

		this.broadcast(currentUsername + " disconnected!", session, allChatRooms.get(session.getId()));
	}

	private void broadcast(String message, Session exclude, String currentChatRoom) {
		allSessions.forEach((username, session) -> {
			try {
				if (!(exclude != null && session.getId().equals(exclude.getId()))) {
					if (allChatRooms.get(session.getId()).equals(currentChatRoom)) {
						session.getBasicRemote().sendObject(message);
					}
				}
			} catch (IOException | EncodeException e) {
				e.printStackTrace();
			}
		});
	}
}
