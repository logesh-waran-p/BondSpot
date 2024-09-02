package com.application.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.application.entity.Match;
import com.application.entity.User;
import com.application.repository.UserRepository;
import com.application.service.MatchService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MatchController {
	
	@Autowired
	private final MatchService matchService;
	private final UserRepository userRepository;
	
	
	
	
	@PostMapping("/match")
    public Match createMatch(@RequestBody Match match) {
        Match createdMatch = matchService.save(
        		match.getUser1().getId(), 
        		match.getUser2().getId(),
        		match.getStatus(),
        		match.getMatchDate());     		
        return createdMatch;
    }
	
	 @PutMapping("/match/{matchId}/status")
	    public ResponseEntity<?> updateMatchStatus(
	            @PathVariable int matchId,
	            @RequestParam String newStatus, 
	            Authentication authentication) {
		 
		 Optional<User> optionalUser = userRepository.findByUsername(authentication.getName());   		
		    
		    if (!optionalUser.isPresent()) {
		        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		    }
		    Match updatedMatch = matchService.updateMatchStatus(matchId, newStatus);
		    User authenticatedUser = optionalUser.get();
		    if (authenticatedUser.getId() != updatedMatch.getUser1().getId() && authenticatedUser.getId() != updatedMatch.getUser2().getId()) {
		        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("username or password invalid please check");
		    }			 
	        	        
	        Map<String, Object> updateStatus = new HashMap<>();
	        updateStatus.put("id", updatedMatch.getId());
	        updateStatus.put("user1", updatedMatch.getUser1().getId());
	        updateStatus.put("user2", updatedMatch.getUser2().getId());
	        updateStatus.put("status", updatedMatch.getStatus());
	        updateStatus.put("matchDate", updatedMatch.getMatchDate());	        
	        return ResponseEntity.ok(updateStatus);
	    }
	
	
	
	@GetMapping("/match")
	public List<String> findAll() {
		List<String> matchList = matchService.getAllMatches();
		
		if(matchList.isEmpty()) {
			throw new RuntimeException("Match Id not found: "+matchList); 
		}
		return matchList;
	}
	
	@GetMapping("/match/{matchId}")
	public ResponseEntity<?> findById(@PathVariable int matchId, Authentication authentication) {
		Optional<User> optionalUser = userRepository.findByUsername(authentication.getName());	
		
		User authenticatedUser = optionalUser.get();		
		if (!optionalUser.isPresent()) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }
	    
		Match match = matchService.getMatchById(matchId);	    
	    if (match.getId() != matchId) {
	    	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No match found with the ID: " + matchId);
	    }
	    
	    if((authenticatedUser.getId()!= match.getUser1().getId()) && (authenticatedUser.getId() != match.getUser2().getId())) {
	    	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("username or password invalid please check");
	    }	    	    
		
		 Map<String, Object> matchMap = new HashMap<>();
		    matchMap.put("id", match.getId());
		    matchMap.put("user1", match.getUser1().getId());  //user1 is an entity and I want to return its ID
		    matchMap.put("user2", match.getUser2().getId());
		    matchMap.put("status", match.getStatus());
		    matchMap.put("matchDate", match.getMatchDate());
		
		return ResponseEntity.ok(matchMap);
	}
	
	
	
	
	@GetMapping("/match/user/{userId}")
	public ResponseEntity<?> findMatchByUserId(@PathVariable int userId, Authentication authentication) {
		Optional<User> optionalUser = userRepository.findByUsername(authentication.getName());
		
		User authenticatedUser = optionalUser.get();		
		if (!optionalUser.isPresent()) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }	    
			    
	    if (authenticatedUser.getId() != userId) {
	    	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("username or password invalid");
	    }
		
		List<Match> matches = matchService.findMatchByUserId(userId);   
        
        List<Map<String, Object>> result = new ArrayList<>();
        for (Match match : matches) {            
            if (authenticatedUser.getId() == match.getUser1().getId() || authenticatedUser.getId() == match.getUser2().getId() ) {
            	Map<String, Object> matchMap = new HashMap<>();
            	matchMap.put("id", match.getId());
                matchMap.put("user1", match.getUser1().getId());
                matchMap.put("user2", match.getUser2().getId());
                matchMap.put("status", match.getStatus());
                matchMap.put("matchDate", match.getMatchDate());
                result.add(matchMap);
            }
            
        }
		return ResponseEntity.ok(result);
		
	}
	
	@GetMapping("/match/status/{status}")
	public ResponseEntity<?> findMatchByStatus(@PathVariable String status, Authentication authentication) {
		
		Optional<User> optionalUser = userRepository.findByUsername(authentication.getName());
		
		User authenticatedUser = optionalUser.get();		
		if (!optionalUser.isPresent()) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }  		
	    
		List<Match> matches = matchService.findMatchByStatus(status);
		
		
        if (matches.isEmpty()) {
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No matches found with status: " + status);
        }        
      
        
        List<Map<String, Object>> result = new ArrayList<>();
        for (Match match : matches) {
        	  if (authenticatedUser.getId() == match.getUser1().getId() || authenticatedUser.getId() == match.getUser2().getId() ) {
//      	        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();       	
		            Map<String, Object> matchMap = new HashMap<>();
		            matchMap.put("id", match.getId());
		            matchMap.put("user1", match.getUser1().getId());
		            matchMap.put("user2", match.getUser2().getId());
		            matchMap.put("status", match.getStatus());
		            matchMap.put("matchDate", match.getMatchDate());
		            result.add(matchMap);
        	  }
        }
		return ResponseEntity.ok(result);
		
	}
	
	//List<Map<String, Object>>
	
	@GetMapping("/match/user/{userId}/status/{status}")
    public ResponseEntity<?> findMatchByUserIdAndStatus(@PathVariable int userId, @PathVariable String status, Authentication authentication) {
       
		Optional<User> optionalUser = userRepository.findByUsername(authentication.getName());
		
		User authenticatedUser = optionalUser.get();		
		if (!optionalUser.isPresent()) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }  		
		
		if (authenticatedUser.getId() != userId) {
	    	return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No users found with userID: " + userId);
	    }
	    
		List<Match> matches = matchService.findMatchByUserIdAndStatus(userId, status);		
		
        if (matches.isEmpty()) {
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No matches found with status: " + status);
        }		
        

        List<Map<String, Object>> result = new ArrayList<>();
        for (Match match : matches) {
        	
        	if ((authenticatedUser.getId() == match.getUser1().getId() || authenticatedUser.getId() == match.getUser2().getId()) 
        			&& match.getStatus().equals(status)) {
	            Map<String, Object> matchMap = new HashMap<>();
	            matchMap.put("id", match.getId());
	            matchMap.put("user1", match.getUser1().getId());
	            matchMap.put("user2", match.getUser2().getId());
	            matchMap.put("status", match.getStatus());
	            matchMap.put("matchDate", match.getMatchDate());
	            result.add(matchMap);
        	}
        }
        return ResponseEntity.ok(result);
    }
	
	
	//delete mapping is not allowed by users
	@DeleteMapping("/match/{theId}")
	public String deleteById(@PathVariable int theId) {
		 matchService.delete(theId);
		 return "the match id "+theId+" was deleted successfully";
	}
	


}
