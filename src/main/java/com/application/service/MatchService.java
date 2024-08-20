package com.application.service;

import java.time.LocalDateTime;
import java.util.List;
import com.application.entity.Match;

public interface MatchService {
	
	List<String> getAllMatches();
	
	Match getMatchById(int id);		
	
	void delete(int id);
	
	Match save(int userId1, int userId2, String status, LocalDateTime matchDate);
	
	List<Match> findMatchByUserId(int userId);

	List<Match> findMatchByStatus(String status);
	
	List<Match> findMatchByUserIdAndStatus(int userId, String status);

	Match updateMatchStatus(int matchId, String newStatus);
	
	
	

}
