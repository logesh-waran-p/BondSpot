package com.application.entity;

import java.time.LocalDateTime;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="matches")
public class Match {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.DETACH,
			CascadeType.MERGE, CascadeType.PERSIST})
	@JoinColumn(name="user_id_1", nullable = false)
	private User user1;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.DETACH,
			CascadeType.MERGE, CascadeType.PERSIST})
	@JoinColumn(name="user_id_2", nullable = false)
	private User user2;
	
	@Column(name="match_date", updatable = false)
	private LocalDateTime matchDate;
	
	
	
	@Column(name="status")
	private String status;
	
	public Match() {
		
	}	


	public Match(int id, User user1, LocalDateTime matchDate, String status) {
		
		this.id = id;	
		this.user1 = user1;
		this.matchDate = matchDate;
		this.status = status;
	}
	
	


	public Match(int id, User user1, User user2, LocalDateTime matchDate, String status) {
		super();
		this.id = id;
		this.user1 = user1;
		this.user2 = user2;
		this.matchDate = matchDate;
		this.status = status;
	}


	public Match(User user1, User user2, LocalDateTime matchDate, String status) {
		
		this.user1 = user1;
		this.user2 = user2;
		this.matchDate = matchDate;
		this.status = status;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser1() {
		return user1;
	}

	public void setUser1(User user1) {
		this.user1 = user1;
	}

	public User getUser2() {
		return user2;
	}

	public void setUser2(User user2) {
		this.user2 = user2;
	}

	public LocalDateTime getMatchDate() {
		return matchDate;
	}

	public void setMatchDate(LocalDateTime matchDate) {
		this.matchDate = matchDate;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}



	
	

}
