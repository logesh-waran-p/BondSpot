package com.application.security;

import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.application.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SpringSecurityUserDetailsService implements UserDetailsService {

	
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.application.entity.User user =  userRepository.findByUsername(username)
		.orElseThrow(()-> new UsernameNotFoundException("UserName Not Found "+username));
		List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getRole()));
		return new User(user.getUsername(), user.getPasswords(), authorities);
	}
}
