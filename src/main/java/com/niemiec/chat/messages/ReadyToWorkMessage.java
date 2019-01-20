package com.niemiec.chat.messages;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ReadyToWorkMessage implements Serializable {
	private String nick;
	
	public ReadyToWorkMessage(String nick) {
		this.nick = nick;
	}
	
	public String getNick() {
		return nick;
	}
}
