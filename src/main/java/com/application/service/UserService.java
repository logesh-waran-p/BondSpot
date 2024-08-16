package com.application.service;

import java.util.List;


import com.application.entity.User;

public interface UserService {
	
	List<String> findAll();
	
	User findById(int id);
	
	User save(User user);
	
	void deleteById(int id);
	
	
	
	

}
