package com.mojocial.dto;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class LoginResp extends ResponseEntity<Object> {

	public LoginResp() {
		super(HttpStatus.OK);
	}
	public LoginResp(String token, long expiry) {
		super( ("{token:"+token+",expiry:"+expiry+"}"), HttpStatus.OK);
	}
	public LoginResp(Object obj, HttpStatus status) {
		super(obj, status);
	}
}
