package com.niemiec.chat.messages;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CheckNickMessage implements Serializable {
	private String nick;
	private boolean nickNotExist;
	
	public CheckNickMessage(String nick) {
		this.nick = nick;
		this.nickNotExist = false;
	}

	public boolean isNickNotExist() {
		return nickNotExist;
	}

	public void nickNotExist() {
		this.nickNotExist = true;
	}

	public String getNick() {
		return nick;
	}
}