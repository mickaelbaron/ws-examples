package fr.mickaelbaron.chatbinarywebsocket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import jakarta.websocket.EncodeException;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
@ServerEndpoint(value = "/chat/{chatroom}/{username}")
public class ChatBinaryEndpoint {

	// Key = username
	private static Map<String, Session> allSessions = new HashMap<>();

	// Key = session id
	private static Map<String, String> allUsers = new HashMap<>();

	// Key = session id
	private static Map<String, String> allChatRooms = new HashMap<>();

	@OnOpen
	public void onOpen(Session session, @PathParam("chatroom") String chatRoom, @PathParam("username") String userName)
			throws IOException {
		System.out.println("ChatEndpoint.onOpen()");

		System.out.println("PathParameters:");
		session.getPathParameters().forEach((c, v) -> {
			System.out.println(c + " " + v);
		});

		System.out.println("QueryParameters:");
		System.out.println(session.getQueryString());

		System.out.println("Path and Query Parameters:");
		session.getRequestParameterMap().forEach((k, v) -> {
			System.out.println(k + " " + v);
		});

		System.out.println("Connection number:" + session.getOpenSessions().size());

		if (allSessions.containsKey(userName)) {
			session.close();
		} else {
			allChatRooms.put(session.getId(), chatRoom);
			allUsers.put(session.getId(), userName);
			allSessions.put(userName, session);
			this.broadcastStringMessage(userName + " connected!", session, chatRoom);
		}
	}

	@OnMessage
	public void onMessage(byte[] message, Session session) {
		System.out.println("ChatEndpoint.onMessageForByteArray()");

		this.broadcastBinaryMessage(ByteBuffer.wrap(message), null, allChatRooms.get(session.getId()));
	}

	@OnMessage
	public void onMessage(String message, Session session) {
		System.out.println("ChatEndpoint.onMessage()");

		this.broadcastStringMessage(message + " (from: " + allUsers.get(session.getId()) + ")", null,
				allChatRooms.get(session.getId()));
	}

	@OnClose
	public void onClose(Session session) {
		System.out.println("ChatEndpoint.onClose()");

		String currentUsername = allUsers.get(session.getId());
		allSessions.remove(currentUsername);
		allUsers.remove(session.getId());

		this.broadcastStringMessage(currentUsername + " disconnected!", session, allChatRooms.get(session.getId()));
	}

	private void broadcastStringMessage(String message, Session exclude, String currentChatRoom) {
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

	private void broadcastBinaryMessage(ByteBuffer message, Session exclude, String currentChatRoom) {
		allSessions.forEach((username, session) -> {
			try {
				if (!(exclude != null && session.getId().equals(exclude.getId()))) {
					if (allChatRooms.get(session.getId()).equals(currentChatRoom)) {
						Future<Void> sendBinary = session.getAsyncRemote().sendBinary(message);
						sendBinary.get(1, TimeUnit.MILLISECONDS);
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			} catch (TimeoutException e) {
				System.out.println("Too long Duke!!!");

				e.printStackTrace();
			}
		});
	}
}
