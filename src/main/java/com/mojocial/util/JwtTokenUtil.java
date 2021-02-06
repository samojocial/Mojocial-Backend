package com.mojocial.util;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Value("${jwt.secret}")
	private String secretKey = "harish";
	
	private static final long JWT_VALIDITY_SECONDS = 3600;
	
	public String getUnameFromToken(String token) {
		return getClaimsFromToken(token, Claims::getSubject);
	}
	public Date getExpirationDateFromToken(String token) {
		return getClaimsFromToken(token, Claims::getExpiration);
	}
	public <T>T getClaimsFromToken(String token, Function<Claims, T> claimResolver) {
		final Claims claims = getAllCalims(token);
		return claimResolver.apply(claims);
	}
	public Claims getAllCalims(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
	}
	public boolean isTokenExpired(String token) {
		Date dt = getExpirationDateFromToken(token);
		return dt.before(new Date());
	}
	public TokenDto generateToken(String uname) {
		Map<String, Object> claims = new HashMap();
		return doGenerateToken(claims, uname);
	}
	public TokenDto doGenerateToken(Map<String, Object> claims, String subject) {
		Date currentDt = new Date();
		Date validDt = new Date(System.currentTimeMillis() + JWT_VALIDITY_SECONDS*1000);
		TokenDto dto = new TokenDto();
		String token = Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(currentDt)
				.setExpiration(validDt).signWith(SignatureAlgorithm.HS512, secretKey).compact();
		dto.setToken(token);
		dto.setExpiry(validDt.getTime());
		return dto;
	}
	public boolean isValidToken(String token, String uname) {
		return uname.equals(getUnameFromToken(token)) && !isTokenExpired(token);
	}
	
	//public static void main(String[] args) {
		/*
		String header = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJoYXJpc2hAZ21haWwuY29tIiwiZXhwIjoxNjAyNDkwOTYyLCJpYXQiOjE2MDI0ODczNjJ9.85GCfQt31vJF_BD5Gzbu2fTrlTgT2vbhtId0c2q9qeZYaJPzcgkisLhTvBZH-JIsULaSe0PIY8vWUR8CR9t3RQ";
		String token = header.substring(7);
		String uname = "";
		JwtTokenUtil tokenUtil = new JwtTokenUtil();
		uname = tokenUtil.getUnameFromToken(token);
		System.out.println("uname = "+uname);
		*/
		/*
		String storeName = "Sri Balaji Sweets";
		String tagid = storeName.toLowerCase().replaceAll(" ", "-").replaceAll(",", "-").replaceAll("\\.", "-");
		System.out.println("tagid = "+tagid);
		*/
	//}
}
