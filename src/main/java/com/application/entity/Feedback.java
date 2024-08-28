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
@Table(name="feedbacks")
public class Feedback {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.DETACH,
			CascadeType.MERGE, CascadeType.PERSIST})
	@JoinColumn(name="users")
	private User user;
	
	@Column(name="feedback")
	private String feedback;
	
	@Column(name="rating")
	private double rating;
	
	@Column(name="created_at")
	private LocalDateTime createdAt;

	public Feedback() {
		super();
	}

	public Feedback(User user, String feedback, double rating, LocalDateTime createdAt) {
		super();
		this.user = user;
		this.feedback = feedback;
		this.rating = rating;
		this.createdAt = createdAt;
	}
	
	

	public Feedback(int id, User user, String feedback, double rating, LocalDateTime createdAt) {
		super();
		this.id = id;
		this.user = user;
		this.feedback = feedback;
		this.rating = rating;
		this.createdAt = createdAt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

		

}
