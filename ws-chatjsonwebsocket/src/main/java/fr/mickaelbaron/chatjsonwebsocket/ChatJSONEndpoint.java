package fr.mickaelbaron.chatjsonwebsocket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
@ServerEndpoint(
		value = "/chat/{chatroom}/{username}",
		decoders = ChatMessageDecoder.class,
		encoders = ChatMessageEncoder.class)
public class ChatJSONEndpoint {

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
	public void onMessage(Session session, byte[] message) {
		System.out.println("ChatEndpoint.onMessageForByteArray()");

		this.broadcastBinaryMessage(ByteBuffer.wrap(message), null, allChatRooms.get(session.getId()));
	}

	@OnMessage
	public void onMessage(Session session, ChatMessage message) {
		System.out.println("ChatEndpoint.onMessage()");
		
		this.broadcastObjectMessage(message, allUsers.get(session.getId()), null,
				allChatRooms.get(session.getId()));
	}
	
	@OnClose
	public void onClose(Session session) {
		System.out.println("ChatEndpoint.onClose()");

		String currentUsername = allUsers.get(session.getId());
		allSessions.remove(currentUsername);
		allUsers.remove(session.getId());

		ChatMessage newChatMessage = new ChatMessage();
		newChatMessage.setContent(currentUsername);
		this.broadcastStringMessage(currentUsername + " disconnected!", session, allChatRooms.get(session.getId()));
	}

	private void broadcastObjectMessage(ChatMessage message, String user, Session exclude, String currentChatRoom) {
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
