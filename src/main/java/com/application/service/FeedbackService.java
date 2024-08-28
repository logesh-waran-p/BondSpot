package com.application.service;

import java.time.LocalDateTime;
import java.util.List;

import com.application.entity.Feedback;


public interface FeedbackService {
	
	Feedback save(int userId, String feedback, double rating, LocalDateTime createdAt);
	
	List<String> getAllFeedback();
	
	Feedback getFeedbackById(int id);
	
	List<Feedback> findFeedbackByUserId(int userId);
	
	void delete(int id);

}
