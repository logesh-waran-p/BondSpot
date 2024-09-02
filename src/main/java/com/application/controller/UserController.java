package com.application.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.application.entity.User;
import com.application.repository.UserRepository;
import com.application.service.UserService;


import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
	
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	

	
	
	@GetMapping("/user")
	public List<String> findAll(){
		List<String> user = userService.findAll();
		if(user.isEmpty()) {
			throw new RuntimeException("There Users Not Available: "+user);
		}
		return user;
	}
	
	@GetMapping("/users")
	public User getUserDetailsAfterLogin(Authentication authentication) {
		Optional<User> optionalCustomer = userRepository.findByUsername(authentication.getName());
		return optionalCustomer.orElse(null);
	}
	
	
	@PostMapping("/user")
	public String createUser(@RequestBody User user) {
		String hashpwd = passwordEncoder.encode(user.getPasswords());
		user.setPasswords(hashpwd);
		User savedUser = userService.save(user);
		if(savedUser.getId()>0) {
			return "user saved successfully";
		}else {
			return "Failed to save user";
		}
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<Map<String, Object>> findById(@PathVariable int userId, Authentication authentication) {
		 Optional<User> optionalUser = userRepository.findByUsername(authentication.getName());
		
		User authenticatedUser = optionalUser.get();
		if (!optionalUser.isPresent()) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }
	    if (authenticatedUser.getId() != userId) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	    }
	    User user = userService.findById(userId);
		
		 Map<String, Object> userMap = new HashMap<>();
		 	userMap.put("id", user.getId());
		 	userMap.put("email",user.getUsername());
		 	userMap.put("password", user.getPasswords());
		 	userMap.put("firstName", user.getFirstName());
		 	userMap.put("lastName", user.getLastName());
		 	userMap.put("dob", user.getDob());
		 	userMap.put("gender", user.getGender());
		 	userMap.put("phoneNumber", user.getPhoneNumber());
		 	userMap.put("address", user.getAddress());
		 	userMap.put("registrationDate", user.getRegistrationDate());	    
		
		return ResponseEntity.ok(userMap);
	}
	

	
	@PutMapping("/user/{userId}")
	public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable int userId, Authentication authentication) {
	    Optional<User> optionalUser = userRepository.findByUsername(authentication.getName());
	    String hashpwd = passwordEncoder.encode(user.getPasswords());
		user.setPasswords(hashpwd);
	    
	    if (!optionalUser.isPresent()) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }
	    
	    User authenticatedUser = optionalUser.get();
	    if (authenticatedUser.getId() != userId) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	    }
	    
	    user.setId(userId);
	    User updatedUser = userService.save(user);
	    return ResponseEntity.ok(updatedUser);
	}
	
	@DeleteMapping("/user/{userId}")
	public ResponseEntity<String> deleteById(@PathVariable int userId, Authentication authentication) {
		Optional<User> optionalUser = userRepository.findByUsername(authentication.getName());
		
		if (!optionalUser.isPresent()) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }
	    
	    User authenticatedUser = optionalUser.get();
	    if (authenticatedUser.getId() != userId) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	    }
		User user = userService.findById(userId);
		if(user==null) {
			throw new RuntimeException("User Id not found: "+user);
		}
		userService.deleteById(userId);
		return ResponseEntity.ok("User Id "+userId +" deleted successfully");
	}
	
	
	

}
