package fr.mickaelbaron.chatjsonwebsocket;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
public class ChatMessageEncoder implements Encoder.Text<ChatMessage> {

	@Override
	public void init(EndpointConfig config) {
	}

	@Override
	public void destroy() {
	}

	@Override
	public String encode(ChatMessage object) throws EncodeException {
		System.out.println("ChatMessageEncoder.encode()");
		
		ObjectMapper mapper = new ObjectMapper(); 
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			
			return "ERROR";
		}
	}

}
