package com.application.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.application.entity.Match;
import com.application.entity.Message;
import com.application.entity.User;
import com.application.repository.UserRepository;
import com.application.service.MessageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MessageController {
	
	private final MessageService messageService;
	private final UserRepository userRepository;
	
	

	@PostMapping("/message")
	public Message sendMessage(@RequestBody Message message) {
	        Message sentMessage = messageService.saveMessage(
	           message.getSender().getId(), 
	           message.getReceiver().getId(), 
	           message.getContent()
	        );	         
	      return sentMessage;	        
	}
	
	
	//not needed for now 
	 @GetMapping("/conversation")
	 public ResponseEntity<?> getConversation(
	            @RequestParam int userId1, 
	            @RequestParam int userId2) {
	      List<Message> messages = messageService.getConversation(userId1, userId2);
	      if (messages.isEmpty()) {
	          return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messages);
	      }
	      return ResponseEntity.ok(messages);
	 }
	 
	//not applicable
	@GetMapping("/message")
	public List<String> findAll() {
		List<String> messageList = messageService.getAllMessage();
			
		if(messageList.isEmpty()) {
			throw new RuntimeException("message not found: "+messageList); 
		}
			return messageList;
	}
	
	@GetMapping("/message/{messageId}")
	public ResponseEntity<?> findById(@PathVariable int messageId, Authentication authentication) {
		Optional<User> optionalUser = userRepository.findByUsername(authentication.getName());	
		
		if (!optionalUser.isPresent()) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }
		
		User authenticatedUser = optionalUser.get();
		
		Message message = messageService.getMessageById(messageId);	 
		
		if (message == null) {
	    	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No message found with the ID: " + messageId);
	    }
		
		if(authenticatedUser.getId()!= message.getSender().getId() && authenticatedUser.getId()!= message.getReceiver().getId()) {
	    	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("username or password invalid please check");
	    }	   
	    
		 Map<String, Object> messageMap = new HashMap<>();
			 messageMap.put("id", message.getId());
			 messageMap.put("senderId", message.getSender().getId());
			 messageMap.put("receiverId", message.getReceiver().getId());
			 messageMap.put("message", message.getContent());
			 messageMap.put("sentAt", message.getSentAt());
		
		return ResponseEntity.ok(messageMap);
	}
	
	
	@GetMapping("/message/sender/{senderId}")
	public ResponseEntity<?> findMessageBySenderId(@PathVariable int senderId, Authentication authentication) {
		Optional<User> optionalUser = userRepository.findByUsername(authentication.getName());	
		
		if (!optionalUser.isPresent()) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }
		
		User authenticatedUser = optionalUser.get();
		
		List<Message> messages = messageService.findMessageBySenderId(senderId);
		
		if (authenticatedUser.getId() != senderId) {
	    	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No sender found with the ID: " + senderId);
	    }
		
        if (messages.isEmpty()) {
        	throw new RuntimeException("Sender Id not found: "+senderId);
        }
        
        List<Map<String, Object>> result = new ArrayList<>();
        for (Message message : messages) {
        	
        	if(authenticatedUser.getId() == message.getSender().getId()) {
        		 Map<String, Object> messageMap = new HashMap<>();
                 messageMap.put("id", message.getId());
                 messageMap.put("senderId", message.getSender().getId());
     			 messageMap.put("receiverId", message.getReceiver().getId());
     			 messageMap.put("message", message.getContent());
     			 messageMap.put("sentAt", message.getSentAt());
                 result.add(messageMap);
    	    }
           
        }
		return ResponseEntity.ok(result);		
	}
	
	@GetMapping("/message/receiver/{receiverId}")
	public ResponseEntity<?> findMessageByReceiverId(@PathVariable int receiverId, Authentication authentication) {
		Optional<User> optionalUser = userRepository.findByUsername(authentication.getName());	
		
		if (!optionalUser.isPresent()) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }
		
		User authenticatedUser = optionalUser.get();
		
		List<Message> messages = messageService.findMessageByReceiverId(receiverId);
		
		if (authenticatedUser.getId() != receiverId) {
	    	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No receiver found with the ID: " + receiverId);
	    }		
		
        if (messages.isEmpty()) {
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No receiver found with the ID: " + receiverId);
        }
        
        List<Map<String, Object>> result = new ArrayList<>();
        for (Message message : messages) {
        	if(authenticatedUser.getId() == message.getReceiver().getId()) {
        		Map<String, Object> messageMap = new HashMap<>();
                messageMap.put("id", message.getId());
                messageMap.put("senderId", message.getSender().getId());
    			 messageMap.put("receiverId", message.getReceiver().getId());
    			 messageMap.put("message", message.getContent());
    			 messageMap.put("sentAt", message.getSentAt());
                result.add(messageMap);
        	}
            
        }
		return ResponseEntity.ok(result);		
	}
	
	//delete mapping is not allowed by users
	@DeleteMapping("/message/{theId}")
	public String deleteById(@PathVariable int theId) {
		 messageService.delete(theId);
		 return "the message id "+theId+" was deleted successfully";
	}
	
	
}
