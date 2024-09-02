package com.application.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.application.entity.Match;
import com.application.entity.User;
import com.application.repository.MatchRepository;
import com.application.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Service
public class MatchServiceImpl implements MatchService{
	@Autowired
	private MatchRepository matchRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	private EntityManager entityManager;
	
	public MatchServiceImpl(MatchRepository theMatchRepository, UserRepository theUserRepository, 
			EntityManager theEntityManager) {
		matchRepository = theMatchRepository;
		userRepository = theUserRepository;
		entityManager = theEntityManager;
		
	}
	
	@Override
	public Match save(int userId1, int userId2, String status, LocalDateTime matchDate) {
		User user1 = userRepository.findById(userId1)
				.orElseThrow(() -> new RuntimeException("User1 not found"));
		
		User user2 = userRepository.findById(userId2)
				.orElseThrow(() -> new RuntimeException("User2 not found"));
		
		 // Check if a match already exists
        Match existingMatch = matchRepository.findByUser1AndUser2(user1, user2);
        if (existingMatch != null) {
            return existingMatch;
        }       
        
		Match saveMatch = new Match();
		saveMatch.setUser1(user1);
		saveMatch.setUser2(user2);
		saveMatch.setStatus(status);
		saveMatch.setMatchDate(matchDate != null ? matchDate : LocalDateTime.now());
		
		System.out.println("Creating match: User1 ID = " + user1.getId() + ", User2 ID = " + user2.getId());
		
		Match newMatch = new Match();
		if(user1.getId() != user2.getId()) {
			newMatch = matchRepository.save(saveMatch);
		}else {
			throw new RuntimeException("the user1 and user2 are same");
		}
		
		
		 System.out.println("Saved match: User1 ID = " + newMatch.getUser1().getId() + ", "
		 		+ "User2 ID = " + newMatch.getUser2().getId());
		return newMatch;
	}
	
	
	@Override
	public List<Match> findMatchByUserId(int theId) {
		//create query
		TypedQuery<Match> query = entityManager.createQuery(
				
		 "SELECT new com.application.entity.Match(m.id, m.user1, m.user2, m.matchDate, m.status) " +
				  "FROM Match m WHERE m.user1 = :user1 OR m.user2 = :user1 ", Match.class);
		
		 // Create a reference to the User entity
	    User userReference = entityManager.getReference(User.class, theId);
		
		query.setParameter("user1", userReference);
		
		//execute query
		List<Match> matches = query.getResultList();
		return matches;
	}
	
	
	@Override
	public List<Match> findMatchByStatus(String status) {
		//create query
		TypedQuery<Match> query = entityManager.createQuery(
				
		 "SELECT new com.application.entity.Match(m.id, m.user1, m.user2, m.matchDate, m.status) " +
				  "FROM Match m WHERE m.status = :status", Match.class);
		
//		 // Create a reference to the User entity
//	    Match userReference = entityManager.getReference(Match.class, status);
		
		query.setParameter("status", status);
		
		//execute query
		List<Match> matches = query.getResultList();
		return matches;
	}

	@Override
	public List<String> getAllMatches() {	
		TypedQuery<String> query = entityManager.createQuery(				
				 "SELECT CONCAT(' id: ', m.id,' user1: ', m.user1, ' user2: ', m.user2,' date: ', "
				 + "m.matchDate, ' status: ', m.status) " +
					        "FROM Match m", String.class);
		
		return query.getResultList();
		
	}

	@Override
	public Match getMatchById(int theId) {		
		
		//create query
		TypedQuery<Match> query = entityManager.createQuery(						
		"SELECT new com.application.entity.Match(m.id, m.user1, m.user2, m.matchDate, m.status) " +
		"FROM Match m WHERE m.id = :id", Match.class);
				
		query.setParameter("id", theId);
				
		//execute query
		return  query.getSingleResult();		
	}

	

	@Override
	public void delete(int id) {
		 matchRepository.deleteById(id);
		
		
	}

	@Override
	public List<Match> findMatchByUserIdAndStatus(int userId, String status) {
		User user1 = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User1 not found"));
		
		User user2 = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User2 not found"));
		
		return matchRepository.findByUser1OrUser2AndStatus(user1, user2, status);
	}

	@Override
	public Match updateMatchStatus(int matchId, String newStatus) {
		Match match = matchRepository.findById(matchId)
	            .orElseThrow(() -> new RuntimeException("Match not found with id: " + matchId));        
	       
	        
	        match.setStatus(newStatus);
	        return matchRepository.save(match);
    }
	
	
}



