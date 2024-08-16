package com.application.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.application.entity.User;
import com.application.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
	
	private UserService userService;
	
	public UserController(UserService theUserService) {
		userService = theUserService;
	}
	
	
	@GetMapping("/user")
	public List<String> findAll(){
		List<String> user = userService.findAll();
		if(user.isEmpty()) {
			throw new RuntimeException("Match Id not found: "+user);
		}
		return user;
	}
	
	
	@PostMapping("/user")
	public User createUser(@RequestBody User user) {
		return userService.save(user);
	}
	
	@GetMapping("/user/{userId}")
	public Map<String, Object> findById(@PathVariable int userId) {
		User user = userService.findById(userId);
		
		 Map<String, Object> userMap = new HashMap<>();
		 	userMap.put("id", user.getId());
		 	userMap.put("email",user.getEmail());
		 	userMap.put("firstName", user.getFirstName());
		 	userMap.put("lastName", user.getLastName());
		 	userMap.put("dob", user.getDob());
		 	userMap.put("gender", user.getGender());
		 	userMap.put("phoneNumber", user.getPhoneNumber());
		 	userMap.put("address", user.getAddress());
		 	userMap.put("registrationDate", user.getRegistrationDate());	    
		
		return userMap;
	}
	
	@PutMapping("/user/{userId}")
	public User updateUser(@RequestBody User user, @PathVariable int userId) {
		user.setId(userId);		
		User theUser = userService.save(user);
		return theUser;
	}
	
	@DeleteMapping("/user/{userId}")
	public String deleteById(@PathVariable int userId) {
		User user = userService.findById(userId);
		if(user==null) {
			throw new RuntimeException("User Id not found: "+user);
		}
		userService.deleteById(userId);
		return "User Id "+userId +" deleted successfully";
	}
	
	
	

}
