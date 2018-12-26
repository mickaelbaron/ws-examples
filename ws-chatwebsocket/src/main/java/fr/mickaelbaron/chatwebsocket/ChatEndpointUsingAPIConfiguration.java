package fr.mickaelbaron.chatwebsocket;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.Endpoint;
import javax.websocket.server.ServerApplicationConfig;
import javax.websocket.server.ServerEndpointConfig;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
public class ChatEndpointUsingAPIConfiguration implements ServerApplicationConfig {

	@Override
	public Set<ServerEndpointConfig> getEndpointConfigs(Set<Class<? extends Endpoint>> endpointClasses) {
		return new HashSet<ServerEndpointConfig>() {
			{
				add(ServerEndpointConfig.Builder.create(ChatEndpointUsingAPI.class, "/chatapi/{chatroom}/{username}")
						.build());
			}
		};
	}

	@Override
	public Set<Class<?>> getAnnotatedEndpointClasses(Set<Class<?>> scanned) {
		return Collections.emptySet();
	}
}
