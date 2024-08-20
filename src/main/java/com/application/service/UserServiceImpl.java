package com.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.application.entity.User;
import com.application.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Service
public class UserServiceImpl implements UserService{
	
	
	
	private UserRepository userRepository;	
	
	private EntityManager entityManager;
	
	
	
	public UserServiceImpl(UserRepository theUserRepository, EntityManager theEntityManager) {
					userRepository = theUserRepository;		
					entityManager = theEntityManager;
					
	}	
	

	@Override
	public List<String> findAll() {		
		TypedQuery<String> query = entityManager.createQuery(
			"SELECT CONCAT(' id: ', u.id,"
						+ "' email: ', u.username,"
						+ "' password: ', u.passwords,"
						+ "' firstName: ', u.firstName,"
						+ "' lastName: ', u.lastName,"
						+ "' dob: ', u.dob,"
						+ "' gender: ', u.gender,"
						+ "' phoneNumber: ', u.phoneNumber,"
						+ "' address: ', u.address,"
						+ "' registrationDate: ', u.registrationDate)"
						+ "From User u ",String.class);	
		
		return query.getResultList();
	}
	
	

	@Override
	public User findById(int theId) {
		//create query
		TypedQuery<User> query = entityManager.createQuery(						
		"SELECT new com.application.entity.User(u.id, u.username, u.passwords, u.firstName, u.lastName, "
		+ "u.dob, u.gender, u.phoneNumber, u.address, u.registrationDate) " +
		"FROM User u WHERE u.id = :id", User.class);
						
		query.setParameter("id", theId);
						
		//execute query
		return  query.getSingleResult();		
	}
	

	@Override
	public User save(User user) {
		User saveUser = userRepository.save(user);		
		return saveUser;
		
	}

	@Override
	public void deleteById(int id) {
		userRepository.deleteById(id);		
	}


	


	


	

	

}
