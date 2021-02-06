package com.mojocial.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static final String[] PUBLIC_MATCHERS = { "/user/login", "/user/register", "/user/resetPassword/**", "/image/**" };
//	private static final String[] PUBLIC_MATCHERS = { "/**" };
	
	//@Autowired
	//private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	@Qualifier("myUserService")
	private UserDetailsService userDetailsService;
	/*
	@Bean
	@Override
	public UserDetailsService userDetailsService() {
		UserDetails user =
			 User.withUsername("user")
				.password("$2a$10$sWszOXuTlN0amQi8vXp4cerb.tJUQo.4FzLAnTCsSqChsYhlLdQWW")
				.roles("USER")
				.build();

		return new InMemoryUserDetailsManager(user);
	}*/
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	

	protected void configure(AuthenticationManagerBuilder auth) throws Exception {	
	//	auth
		//.userDetailsService(userDetailsService)
		//.passwordEncoder(passwordEncoder());
		auth.inMemoryAuthentication().withUser("user").password(passwordEncoder().encode("password")).roles("USER");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	//	JwtAuthentication jwtAuthentication = new JwtAuthentication(authenticationManager());
	//	jwtAuthentication.setFilterProcessesUrl(PUBLIC_MATCHERS[0]);
		http.csrf().disable().cors().disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()	
		.authorizeRequests().antMatchers(PUBLIC_MATCHERS).permitAll()
		.anyRequest().authenticated().and().formLogin()
		.successHandler(successHandler())
		.permitAll();
		//.defaultSuccessUrl("/user/login");
		//.and()
		//.addFilter(jwtAuthentication)
		//.addFilterBefore(new JwtAuthorization(), UsernamePasswordAuthenticationFilter.class);
	}
	
	@Bean
	public AuthenticationSuccessHandler successHandler() {
		return new MySuccessHandler("/user/login");
	}
}
