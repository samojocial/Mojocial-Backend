package com.mojocial.resource;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

	@PostMapping("/login")
	public String login(HttpServletRequest request) {
		String referrer = request.getHeader("Referrer");
		System.out.println("referrer = "+referrer);
		request.getSession().setAttribute("uri_prior_login", referrer);
		return "login";
	}
}
