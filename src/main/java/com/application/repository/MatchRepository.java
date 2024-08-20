package com.application.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.application.entity.Match;
import com.application.entity.User;

@Repository
public interface MatchRepository extends JpaRepository<Match, Integer>{

	Match findByUser1AndUser2(User user1, User user2);
	List<Match> findByUser1OrUser2AndStatus(User user1, User user2, String status);
	
	


}
