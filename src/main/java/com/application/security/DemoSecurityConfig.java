package com.application.security;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

@Configuration
public class DemoSecurityConfig {
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http.authorizeHttpRequests(configurer ->
				configurer
					
				// for user
					.requestMatchers(HttpMethod.GET, "/api/user").permitAll()
					.requestMatchers(HttpMethod.GET, "/api/user/**").authenticated()
					.requestMatchers(HttpMethod.GET, "/api/users").authenticated()
					.requestMatchers(HttpMethod.POST, "/api/user").permitAll()
					.requestMatchers(HttpMethod.PUT, "/api/user/**").authenticated()
					.requestMatchers(HttpMethod.DELETE, "/api/user/**").authenticated()
					
				//for profile
					.requestMatchers(HttpMethod.GET, "/api/profile").permitAll()
					.requestMatchers(HttpMethod.GET, "/api/profile/**").authenticated()
					.requestMatchers(HttpMethod.PUT, "/api/profile/**").authenticated()
					
				//for match
					.requestMatchers(HttpMethod.POST, "/api/match").permitAll()
					.requestMatchers(HttpMethod.GET, "/api/match").permitAll()
					.requestMatchers(HttpMethod.GET, "/api/match/**").authenticated()
					.requestMatchers(HttpMethod.GET, "/api/match/user/**").authenticated()
					.requestMatchers(HttpMethod.GET, "/api/match/status/*").authenticated()
					.requestMatchers(HttpMethod.GET, "/api/match/user/*/status/*").authenticated()
					.requestMatchers(HttpMethod.PUT, "/api/match/*/status").authenticated()
					.requestMatchers(HttpMethod.DELETE, "/api/match/**").denyAll()
				
				//for message
					.requestMatchers(HttpMethod.POST, "/api/message").permitAll()
					.requestMatchers(HttpMethod.GET, "/api/conversation").denyAll()
					.requestMatchers(HttpMethod.GET, "/api/message").denyAll()
					.requestMatchers(HttpMethod.GET, "/api/message/**").authenticated()
					.requestMatchers(HttpMethod.GET, "/api/message/sender/**").authenticated()
					.requestMatchers(HttpMethod.GET, "/api/message/receiver/**").authenticated()
					.requestMatchers(HttpMethod.DELETE, "/api/message/**").denyAll()
					
				//for feedback
					.requestMatchers(HttpMethod.POST, "/api/feedback").permitAll()
					.requestMatchers(HttpMethod.GET, "/api/feedback").permitAll()
					.requestMatchers(HttpMethod.GET, "/api/feedback/**").permitAll()
					.requestMatchers(HttpMethod.GET, "/api/feedback/user/**").permitAll()
					.requestMatchers(HttpMethod.DELETE, "/api/feedback/**").denyAll()
					
				);
		
		//use http basic authendication
		http.httpBasic(Customizer.withDefaults());
		
		//disable cross site request forgery
		http.csrf(csrf -> csrf.disable());
		
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	@Bean
	public CompromisedPasswordChecker compromisedPasswordChecker() {
		return new HaveIBeenPwnedRestApiPasswordChecker();
	}
	
	
	
	
	
	
	
	/*@Bean
	public InMemoryUserDetailsManager userDetailsManager() {
		UserDetails john = User.builder()
				.username("john")
				.password("{noop}test123")
				.roles("EMPLOYEE")
				.build();
		
		UserDetails mary = User.builder()
				.username("mary")
				.password("{noop}test123")
				.roles("EMPLOYEE", "MANAGER")
				.build();
		
		UserDetails susan = User.builder()
				.username("susan")
				.password("{noop}test123")
				.roles("EMPLOYEE", "MANAGER", "ADMIN")
				.build();
		
		return new InMemoryUserDetailsManager(john, mary, susan);
				
	}*/
}
