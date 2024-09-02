package com.application.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.application.entity.Profile;
import com.application.entity.User;
import com.application.repository.UserRepository;
import com.application.service.ProfileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProfileController {
	
private final ProfileService profileService;
private final UserRepository userRepository;
	
	
	
	@GetMapping("/profile")
	public List<Profile> findAll(){		
		return profileService.findAll();
	}
	
	
	//yuki.tanaka@example.jp
	//sarah@12345#
	
	@GetMapping("/profile/{profileId}")
	public ResponseEntity<Profile> findById(@PathVariable int profileId, Authentication authentication) {
		Optional<User> optionalUser = userRepository.findByUsername(authentication.getName());
		
		User authenticatedUser = optionalUser.get();
		if (!optionalUser.isPresent()) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }
		Profile profile = profileService.findById(profileId);
		
	    if (authenticatedUser.getProfileId().getId() != profileId) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	    }
		
		return ResponseEntity.ok(profile);
	}
	
	@PutMapping("/profile/{profileId}")
	public ResponseEntity<Profile> updateUser(@RequestBody Profile profile, @PathVariable int profileId, Authentication authentication) {
		Optional<User> optionalUser = userRepository.findByUsername(authentication.getName());
		
		User authenticatedUser = optionalUser.get();
		if (!optionalUser.isPresent()) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }
	
		
	    if (authenticatedUser.getProfileId().getId() != profileId) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	    }
	    
		profile.setId(profileId);	
		Profile updatedProfile = profileService.save(profile);
		return ResponseEntity.ok(updatedProfile);
	}

	

}
