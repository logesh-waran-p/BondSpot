package com.application.service;

import java.util.List;
import com.application.entity.Profile;


public interface ProfileService {

	Profile findById(int id);
	
	List<Profile> findAll();
	
	Profile save(Profile profile);
}
