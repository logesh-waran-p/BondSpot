package com.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.entity.Message;
import com.application.entity.User;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{

	 List<Message> findBySenderAndReceiver(User sender, User receiver);
}
