package com.mojocial.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mojocial.util.JwtTokenUtil;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	@Qualifier("myUserService")
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String reqTokenHeader = request.getHeader("Authorization");
		String uname = null;
		String token = null;
		System.out.println("reqTokenHeader = "+reqTokenHeader);
		if(reqTokenHeader!=null && reqTokenHeader.startsWith("Bearer ")) {
			token = reqTokenHeader.substring(7);
			try {
				uname = jwtTokenUtil.getUnameFromToken(token);
				System.out.println("uname = "+uname);
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		System.out.println("secontext = "+SecurityContextHolder.getContext().getAuthentication());
		if(uname!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(uname);
			if(jwtTokenUtil.isValidToken(token, userDetails.getUsername())) {
				System.out.println("it is valid token");
				UsernamePasswordAuthenticationToken userAuthToken = new UsernamePasswordAuthenticationToken(userDetails,  null,  userDetails.getAuthorities());
				userAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(userAuthToken);
			}
		}
		filterChain.doFilter(request, response);
	}
}
