package com.mojocial.dto;

import java.io.Serializable;

public class LoginReq implements Serializable {

	protected String username;
	protected String password;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
