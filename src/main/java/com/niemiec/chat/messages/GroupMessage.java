package com.niemiec.chat.messages;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GroupMessage implements Serializable {
	private String senderNick;
	private String message;
	
	public GroupMessage(String senderNick, String message) {
		this.senderNick = senderNick;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public String getSenderNick() {
		return senderNick;
	}
}
