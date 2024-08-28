package com.application.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import com.application.entity.Feedback;
import com.application.entity.User;
import com.application.repository.FeedbackRepository;
import com.application.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Service
public class FeedbackServiceImpl implements FeedbackService{

	private UserRepository userRepository;
	private FeedbackRepository feedbackRepository;
	private EntityManager entityManager;
	
	public FeedbackServiceImpl(UserRepository theUserRepository, FeedbackRepository theFeedbackRepository,
			EntityManager theEntityManager) {
		userRepository = theUserRepository;
		feedbackRepository = theFeedbackRepository;
		entityManager = theEntityManager;
	}
	@Override
	public Feedback save(int userId, String feedback, double rating, LocalDateTime createdAt) {
		User user1 = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User1 not found"));
		
		Feedback newFeedback = new Feedback();
		newFeedback.setUser(user1);
		newFeedback.setFeedback(feedback);
		newFeedback.setRating(rating);
		newFeedback.setCreatedAt(createdAt != null ? createdAt : LocalDateTime.now());
		
		Feedback saveFeedback = new Feedback();
		saveFeedback = feedbackRepository.save(newFeedback);
		return saveFeedback;
	}
	@Override
	public List<String> getAllFeedback() {
		TypedQuery<String> query = entityManager.createQuery(				
				 "SELECT CONCAT(' id: ', m.id,' user: ', m.user, ' feedback: ', m.feedback,' rating: ', "
				 + "m.rating, ' createdAt: ', m.createdAt) " +
					        "FROM Feedback m ", String.class);
		
		return query.getResultList();
	}
	@Override
	public Feedback getFeedbackById(int theId) {
		//create query
		TypedQuery<Feedback> query = entityManager.createQuery(						
		"SELECT new com.application.entity.Feedback(m.id, m.user, m.feedback, m.rating, m.createdAt) " +
		"FROM Feedback m WHERE m.id = :id", Feedback.class);
				
		query.setParameter("id", theId);
				
		//execute query
		return  query.getSingleResult();
	}
	@Override
	public List<Feedback> findFeedbackByUserId(int userId) {
		//create query
				TypedQuery<Feedback> query = entityManager.createQuery(
								
				"SELECT new com.application.entity.Feedback(m.id, m.user, m.feedback, m.rating, m.createdAt) " +
					 "FROM Feedback m WHERE m.user = :user ", Feedback.class);
						
				// Create a reference to the User entity
				User userReference = entityManager.getReference(User.class, userId);
						
				query.setParameter("user", userReference);
						
				//execute query
				List<Feedback> feedbacks = query.getResultList();
				return feedbacks;
	}
	@Override
	public void delete(int id) {
		feedbackRepository.deleteById(id);
		
	}

}
