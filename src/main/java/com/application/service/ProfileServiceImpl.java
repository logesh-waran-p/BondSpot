package com.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.application.entity.Profile;
import com.application.repository.ProfileRepository;

@Service
public class ProfileServiceImpl implements ProfileService{
	
private ProfileRepository profileRepository;
	
	public ProfileServiceImpl(ProfileRepository theProfileRepository) {
		profileRepository = theProfileRepository;
	}

		
	@Override
	public Profile findById(int id) {		
		Optional<Profile> result = profileRepository.findById(id);		
		Profile profile = null;
		
		if(result.isPresent()) {
			profile = result.get();
		}else {
			throw new RuntimeException("user id not found - "+id);
		}	
		
		return profile;
	}


	@Override
	public List<Profile> findAll() {		
		return profileRepository.findAll();
	}


	@Override
	public Profile save(Profile profile) {
		Profile profile2 = profileRepository.save(profile);
		return profile2;
	}
}
