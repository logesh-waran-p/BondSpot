package com.application.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.application.entity.Feedback;
import com.application.service.FeedbackService;

@RestController
@RequestMapping("/api")
public class FeedbackController {
	
	@Autowired
	private FeedbackService feedbackService;
	
	public FeedbackController(FeedbackService theFeedbackService) {
		feedbackService = theFeedbackService;
	}
	

	
	@PostMapping("/feedback")
    public Feedback createMatch(@RequestBody Feedback feedback) {
        Feedback createdFeedback = feedbackService.save(
        		feedback.getUser().getId(), 
        		feedback.getFeedback(),
        		feedback.getRating(),
        		feedback.getCreatedAt());
        return createdFeedback;
    }
	
	@GetMapping("/feedback")
	public List<String> findAll() {
		List<String> feedbackList = feedbackService.getAllFeedback();
			
		if(feedbackList.isEmpty()) {
			throw new RuntimeException("message not found: "+feedbackList); 
		}
			return feedbackList;
	}
	
	@GetMapping("/feedback/{feedbackId}")
	public Map<String, Object> findById(@PathVariable int feedbackId) {
		Feedback feedback = feedbackService.getFeedbackById(feedbackId);
		
		 Map<String, Object> feedbackMap = new HashMap<>();
			 feedbackMap.put("id", feedback.getId());
			 feedbackMap.put("users", feedback.getUser().getId());
			 feedbackMap.put("feedback", feedback.getFeedback());
			 feedbackMap.put("rating", feedback.getRating());
			 feedbackMap.put("createdAt", feedback.getCreatedAt());
		
		return feedbackMap;
	}
	
	@GetMapping("/feedback/user/{userId}")
	public List<Map<String, Object>> findMessageByUserId(@PathVariable int userId) {
		List<Feedback> feedbacks = feedbackService.findFeedbackByUserId(userId);
        if (feedbacks.isEmpty()) {
        	throw new RuntimeException("User Id not found: "+userId);
        }
        
        List<Map<String, Object>> result = new ArrayList<>();
        for (Feedback feedback : feedbacks) {
            Map<String, Object> feedbackMap = new HashMap<>();
	            feedbackMap.put("id", feedback.getId());
				feedbackMap.put("users", feedback.getUser().getId());
				feedbackMap.put("feedback", feedback.getFeedback());
				feedbackMap.put("rating", feedback.getRating());
				feedbackMap.put("createdAt", feedback.getCreatedAt());
		        result.add(feedbackMap);
        }
		return result;		
	}
	
	@DeleteMapping("/feedback/{feedbackId}")
	public String deleteById(@PathVariable int feedbackId) {
		feedbackService.delete(feedbackId);
		return "The Feedback Id "+feedbackId+" deleted successfully";
	}
	
	

}
