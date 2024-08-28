package com.application.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import com.application.entity.Message;
import com.application.entity.User;
import com.application.repository.MessageRepository;
import com.application.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Service
public class MessageServiceImpl implements MessageService{
	
	private MessageRepository messageRepository;
	private UserRepository userRepository;
	private EntityManager entityManager;
	
	public MessageServiceImpl(MessageRepository theMessageRepository, UserRepository theRepository,
			EntityManager theEntityManager) {
		messageRepository = theMessageRepository;
		userRepository = theRepository;
		entityManager = theEntityManager;
	}

	@Override
	public Message saveMessage(int senderId, int receiverId, String content) {
		 User sender = userRepository.findById(senderId)
		            .orElseThrow(() -> new RuntimeException("Sender not found"));
		 User receiver = userRepository.findById(receiverId)
		            .orElseThrow(() -> new RuntimeException("Receiver not found"));
		
		 Message message = new Message();
		 message.setReceiver(receiver);
		 message.setSender(sender);
		 message.setSentAt(LocalDateTime.now());
		 message.setContent(content);
		 
		 return messageRepository.save(message);	 
		 
	}

	@Override
	public List<Message> getConversation(int userId1, int userId2) {		
		  User user1 = userRepository.findById(userId1)
		      .orElseThrow(() -> new RuntimeException("User1 not found"));
		  User user2 = userRepository.findById(userId2)
		      .orElseThrow(() -> new RuntimeException("User2 not found"));
		  
		  List<Message> messages = messageRepository.findBySenderAndReceiver(user1, user2);
	        messages.addAll(messageRepository.findBySenderAndReceiver(user2, user1));
	        messages.sort((m1, m2) -> m1.getSentAt().compareTo(m2.getSentAt()));
	        
	      return messages;
	}

	@Override
	public List<String> getAllMessage() {
		TypedQuery<String> query = entityManager.createQuery(				
				 "SELECT CONCAT(' id: ', m.id,' senderId: ', m.sender, ' receiverId: ', m.receiver,' message: ', "
				 + "m.content, ' sentAt: ', m.sentAt) " +
					        "FROM Message m ", String.class);
		
		return query.getResultList();
	}

	@Override
	public Message getMessageById(int theId) {
				//create query
				TypedQuery<Message> query = entityManager.createQuery(						
				"SELECT new com.application.entity.Message(m.id, m.sender, m.receiver, m.content, m.sentAt) " +
				"FROM Message m WHERE m.id = :id", Message.class);
						
				query.setParameter("id", theId);
						
				//execute query
				return  query.getSingleResult();
	}

	@Override
	public List<Message> findMessageBySenderId(int senderId) {
		//create query
		TypedQuery<Message> query = entityManager.createQuery(
						
		"SELECT new com.application.entity.Message(m.id, m.sender, m.receiver, m.content, m.sentAt) " +
			 "FROM Message m WHERE m.sender = :sender ", Message.class);
				
		// Create a reference to the User entity
		User userReference = entityManager.getReference(User.class, senderId);
				
		query.setParameter("sender", userReference);
				
		//execute query
		List<Message> messages = query.getResultList();
		return messages;
	}

	@Override
	public List<Message> findMessageByReceiverId(int receiverId) {
		//create query
		TypedQuery<Message> query = entityManager.createQuery(
								
		"SELECT new com.application.entity.Message(m.id, m.sender, m.receiver, m.content, m.sentAt) " +
		"FROM Message m WHERE m.receiver = :receiver ", Message.class);
						
		// Create a reference to the User entity
		User userReference = entityManager.getReference(User.class, receiverId);
//		System.out.println(receiverId);
		query.setParameter("receiver", userReference);
						
		//execute query
		List<Message> messages = query.getResultList();
		return messages;
	}

	@Override
	public void delete(int id) {
		messageRepository.deleteById(id);
		
	}
	
	

}
