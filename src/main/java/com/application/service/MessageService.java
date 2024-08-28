package com.application.service;

import java.util.List;
import com.application.entity.Message;

public interface MessageService {
	
	Message saveMessage(int senderId, int receiverId, String content);
	
	List<Message> getConversation(int userId1, int userId2);
	
	List<String> getAllMessage();
	
	Message getMessageById(int id);
	
	List<Message> findMessageBySenderId(int senderId);
	
	List<Message> findMessageByReceiverId(int receiverId);
	
	void delete(int id);
	
	
	

}
