package com.niemiec.chat.messages;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ExitMessage implements Serializable {
	private String nick;
	
	public ExitMessage(String nick) {
		this.nick = nick;
	}

	public String getNick() {
		return nick;
	}
}
