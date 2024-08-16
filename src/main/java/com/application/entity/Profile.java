package com.application.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="profile")
public class Profile {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="bio")
	private String bio;
	
	@Column(name="occupation")
	private String occupation;
	
	@Column(name="education")
	private String education;
	
	@Column(name="religion")
	private String religion;
	
	@Column(name="mother_tongue")
	private String motherTongue;
	
	
	@Column(name="marital_status")
	private String maritalStatus;
	
	@Column(name="height")
	private double height;
	
	@Column(name="weight")
	private double weight;	
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "profileId")
	private User user;
	

	public Profile() {
		
	}	

	
	public Profile(String bio, String occupation, String education, String religion, String motherTongue,
			String maritalStatus, double height, double weight, User user) {
		super();
		this.bio = bio;
		this.occupation = occupation;
		this.education = education;
		this.religion = religion;
		this.motherTongue = motherTongue;
		this.maritalStatus = maritalStatus;
		this.height = height;
		this.weight = weight;
		this.user = user;
	}




	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public String getMotherTongue() {
		return motherTongue;
	}

	public void setMotherTongue(String motherTongue) {
		this.motherTongue = motherTongue;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
	

	
}
