package org.psw_isa.psw_isa_backend.dtos;

import java.time.LocalDateTime;


public class FormDTO {
	
	private LocalDateTime submissionExpiryTime;
	private String name; 
	private String description;

	public FormDTO(LocalDateTime submissionExpiryTime, String name, String description) {
		this.submissionExpiryTime = submissionExpiryTime;
		this.name = name;
		this.description = description;
	}
	
	public FormDTO() {

	}


	public LocalDateTime getSubmissionExpiryTime() {
		return submissionExpiryTime;
	}

	public void setSubmissionExpiryTime(LocalDateTime submissionExpiryTime) {
		this.submissionExpiryTime = submissionExpiryTime;
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

	

}
