package com.application.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.application.entity.Profile;
import com.application.service.ProfileService;

@RestController
@RequestMapping("/api")
public class ProfileController {
	
private ProfileService profileService;
	
	public ProfileController(ProfileService theProfileService) {
		profileService = theProfileService;
	}
	
	@GetMapping("/profile")
	public List<Profile> findAll(){
		return profileService.findAll();
	}
	
	
	@GetMapping("/profile/{profileId}")
	public Profile findById(@PathVariable int profileId) {
		Profile profile = profileService.findById(profileId);
		if(profile==null) {
			throw new RuntimeException("User Id not found: "+profile);
		}
		return profile;
	}
	
	@PutMapping("/profile/{profileId}")
	public Profile updateUser(@RequestBody Profile profile, @PathVariable int profileId) {
		profile.setId(profileId);	
		Profile theProfile = profileService.save(profile);
		return theProfile;
	}

	

}
