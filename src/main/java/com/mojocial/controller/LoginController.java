package com.mojocial.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mojocial.dto.LoginReq;
import com.mojocial.dto.LoginResp;
import com.mojocial.util.JwtTokenUtil;
import com.mojocial.util.TokenDto;

@RestController
public class LoginController {

	@Autowired
	public JwtTokenUtil jwtTokenUtil;
	
	/*
	@PostMapping("/login")
	public String login(HttpServletRequest request) {
		String referrer = request.getHeader("Referrer");
		System.out.println("referrer = "+referrer);
		request.getSession().setAttribute("uri_prior_login", referrer);
		return "login";
	}*/
	
	@PostMapping("/loginSubmit")
	public ResponseEntity<?> login(@RequestBody LoginReq loginReq) {
		String token = "";
		long expiry = 0;
		if(loginReq.getUsername().equals("akash") && loginReq.getPassword().equals("akash")) {
			TokenDto dto = jwtTokenUtil.generateToken(loginReq.getUsername());
			token = dto.getToken();
			expiry = dto.getExpiry();
			return new LoginResp(token, expiry);
		}
		return new LoginResp("User not found", HttpStatus.FORBIDDEN);
	}
}
