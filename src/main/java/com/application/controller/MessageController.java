package com.application.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.application.entity.Message;
import com.application.service.MessageService;

@RestController
@RequestMapping("/api")
public class MessageController {
	
	private MessageService messageService;
	
	public MessageController(MessageService theMessageService) {
		messageService = theMessageService;
	}

	@PostMapping("/message")
	public Message sendMessage(@RequestBody Message message) {
	        Message sentMessage = messageService.saveMessage(
	           message.getSender().getId(), 
	           message.getReceiver().getId(), 
	           message.getContent()
	        );	         
	      return sentMessage;	        
	}
	
	
	
	 @GetMapping("/conversation")
	 public List<Message> getConversation(
	            @RequestParam int userId1, 
	            @RequestParam int userId2) {
	      List<Message> messages = messageService.getConversation(userId1, userId2);
	      return messages;
	 }
	 
	@GetMapping("/message")
	public List<String> findAll() {
		List<String> messageList = messageService.getAllMessage();
			
		if(messageList.isEmpty()) {
			throw new RuntimeException("message not found: "+messageList); 
		}
			return messageList;
	}
	
	@GetMapping("/message/{messageId}")
	public Map<String, Object> findById(@PathVariable int messageId) {
		Message message = messageService.getMessageById(messageId);
		
		 Map<String, Object> messageMap = new HashMap<>();
			 messageMap.put("id", message.getId());
			 messageMap.put("senderId", message.getSender().getId());
			 messageMap.put("receiverId", message.getReceiver().getId());
			 messageMap.put("message", message.getContent());
			 messageMap.put("sentAt", message.getSentAt());
		
		return messageMap;
	}
	
	
	@GetMapping("/message/sender/{senderId}")
	public List<Map<String, Object>> findMessageBySenderId(@PathVariable int senderId) {
		List<Message> messages = messageService.findMessageBySenderId(senderId);
        if (messages.isEmpty()) {
        	throw new RuntimeException("Sender Id not found: "+senderId);
        }
        
        List<Map<String, Object>> result = new ArrayList<>();
        for (Message message : messages) {
            Map<String, Object> messageMap = new HashMap<>();
            messageMap.put("id", message.getId());
            messageMap.put("senderId", message.getSender().getId());
			 messageMap.put("receiverId", message.getReceiver().getId());
			 messageMap.put("message", message.getContent());
			 messageMap.put("sentAt", message.getSentAt());
            result.add(messageMap);
        }
		return result;		
	}
	
	@GetMapping("/message/receiver/{receiverId}")
	public List<Map<String, Object>> findMessageByReceiverId(@PathVariable int receiverId) {
		List<Message> messages = messageService.findMessageByReceiverId(receiverId);
        if (messages.isEmpty()) {
        	throw new RuntimeException("Receiver Id not found: "+receiverId);
        }
        
        List<Map<String, Object>> result = new ArrayList<>();
        for (Message message : messages) {
            Map<String, Object> messageMap = new HashMap<>();
            messageMap.put("id", message.getId());
            messageMap.put("senderId", message.getSender().getId());
			 messageMap.put("receiverId", message.getReceiver().getId());
			 messageMap.put("message", message.getContent());
			 messageMap.put("sentAt", message.getSentAt());
            result.add(messageMap);
        }
		return result;		
	}
	
	
	@DeleteMapping("/message/{theId}")
	public String deleteById(@PathVariable int theId) {
		 messageService.delete(theId);
		 return "the message id "+theId+" was deleted successfully";
	}
	
	
}
