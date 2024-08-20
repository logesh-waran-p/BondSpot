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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.application.entity.Match;
import com.application.service.MatchService;

@RestController
@RequestMapping("/api")
public class MatchController {
	
	@Autowired
	private MatchService matchService;
	
	public MatchController(MatchService theMatchService) {
		matchService = theMatchService;
	}
	
	
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
	    public Map<String, Object> updateMatchStatus(
	            @PathVariable int matchId,
	            @RequestParam String newStatus) {
	        Match updatedMatch = matchService.updateMatchStatus(matchId, newStatus);	        
	        Map<String, Object> updateStatus = new HashMap<>();
	        updateStatus.put("id", updatedMatch.getId());
	        updateStatus.put("user1", updatedMatch.getUser1().getId());
	        updateStatus.put("user2", updatedMatch.getUser2().getId());
	        updateStatus.put("status", updatedMatch.getStatus());
	        updateStatus.put("matchDate", updatedMatch.getMatchDate());
	        
	        return updateStatus;
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
	public Map<String, Object> findById(@PathVariable int matchId) {
		Match match = matchService.getMatchById(matchId);
		
		 Map<String, Object> matchMap = new HashMap<>();
		    matchMap.put("id", match.getId());
		    matchMap.put("user1", match.getUser1().getId());  //user1 is an entity and I want to return its ID
		    matchMap.put("user2", match.getUser2().getId());
		    matchMap.put("status", match.getStatus());
		    matchMap.put("matchDate", match.getMatchDate());
		
		return matchMap;
	}
	
	
	
	
	@GetMapping("/match/user/{userId}")
	public List<Map<String, Object>> findMatchByUserId(@PathVariable int userId) {
		List<Match> matches = matchService.findMatchByUserId(userId);
        if (matches.isEmpty()) {
        	throw new RuntimeException("Match Id not found: "+userId);
        }
        
        List<Map<String, Object>> result = new ArrayList<>();
        for (Match match : matches) {
            Map<String, Object> matchMap = new HashMap<>();
            matchMap.put("id", match.getId());
            matchMap.put("user1", match.getUser1().getId());
            matchMap.put("user2", match.getUser2().getId());
            matchMap.put("status", match.getStatus());
            matchMap.put("matchDate", match.getMatchDate());
            result.add(matchMap);
        }
		return result;
		
	}
	
	@GetMapping("/match/status/{status}")
	public List<Map<String, Object>> findMatchByStatus(@PathVariable String status) {
		List<Match> matches = matchService.findMatchByStatus(status);
        if (matches.isEmpty()) {
        	throw new RuntimeException("Match status not found: "+status);
        }
        
        List<Map<String, Object>> result = new ArrayList<>();
        for (Match match : matches) {
            Map<String, Object> matchMap = new HashMap<>();
            matchMap.put("id", match.getId());
            matchMap.put("user1", match.getUser1().getId());
            matchMap.put("user2", match.getUser2().getId());
            matchMap.put("status", match.getStatus());
            matchMap.put("matchDate", match.getMatchDate());
            result.add(matchMap);
        }
		return result;
		
	}
	
	
	@GetMapping("/match/user/{userId}/status/{status}")
    public List<Map<String, Object>> findMatchByUserIdAndStatus(@PathVariable int userId, @PathVariable String status) {
        List<Match> matches = matchService.findMatchByUserIdAndStatus(userId, status);
        if (matches.isEmpty()) {
            throw new RuntimeException("No matches found for User ID: " + userId + " with status: " + status);
        }

        List<Map<String, Object>> result = new ArrayList<>();
        for (Match match : matches) {
            Map<String, Object> matchMap = new HashMap<>();
            matchMap.put("id", match.getId());
            matchMap.put("user1", match.getUser1().getId());
            matchMap.put("user2", match.getUser2().getId());
            matchMap.put("status", match.getStatus());
            matchMap.put("matchDate", match.getMatchDate());
            result.add(matchMap);
        }
        return result;
    }
	
	
	
	@DeleteMapping("/match/{theId}")
	public String deleteById(@PathVariable int theId) {
		 matchService.delete(theId);
		 return "the match id "+theId+" was deleted successfully";
	}
	


}
