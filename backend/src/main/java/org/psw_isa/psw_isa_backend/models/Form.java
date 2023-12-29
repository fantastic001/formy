package org.psw_isa.psw_isa_backend.models;

import java.time.LocalDateTime;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;

@Entity
public class Form {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	
	@NotNull
	@PastOrPresent
	private LocalDateTime createTime; 
	
	
	private LocalDateTime submissionExpiryTime;

	@ManyToOne
	@NotNull
	private User author; 

	@NotNull
	private String name; 

	private String description;

	@OneToMany
	Collection<FormSubmission> submissions; 
	
	public Form() {
		
	}

	

	public Form(LocalDateTime createTime, LocalDateTime submissionExpiryTime, User author, String name,
			String description) {
		this.createTime = createTime;
		this.submissionExpiryTime = submissionExpiryTime;
		this.author = author;
		this.name = name;
		this.description = description;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public LocalDateTime getSubmissionExpiryTime() {
		return submissionExpiryTime;
	}

	public void setSubmissionExpiryTime(LocalDateTime submissionExpiryTime) {
		this.submissionExpiryTime = submissionExpiryTime;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}



	public Collection<FormSubmission> getSubmissions() {
		return submissions;
	}

}
