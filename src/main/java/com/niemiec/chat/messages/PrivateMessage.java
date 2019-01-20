package com.niemiec.chat.messages;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PrivateMessage implements Serializable {
	private String senderNick;
	private String recipientNick;
	private String message;
	
	public PrivateMessage(String senderNick, String recipientNick, String message) {
		this.senderNick = senderNick;
		this.recipientNick = recipientNick;
		this.message = message;
	}

	public String getSenderNick() {
		return senderNick;
	}

	public String getRecipientNick() {
		return recipientNick;
	}

	public String getMessage() {
		return message;
	}
}
