package fr.mickaelbaron.chatjsonwebsocket;

import java.util.Date;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
public class ChatMessage {

	private String content;
	
	private Date created;
	
	private String browser;

	public ChatMessage() {
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	@Override
	public String toString() {
		return "ChatMessage [content=" + content + ", created=" + created + ", browser=" + browser + "]";
	}
}
