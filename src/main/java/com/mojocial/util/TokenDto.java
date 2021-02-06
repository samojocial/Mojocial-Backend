package com.mojocial.util;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class TokenDto implements Serializable {

	private String token;
	private long expiry;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public long getExpiry() {
		return expiry;
	}
	public void setExpiry(long expiry) {
		this.expiry = expiry;
	}
	
}
