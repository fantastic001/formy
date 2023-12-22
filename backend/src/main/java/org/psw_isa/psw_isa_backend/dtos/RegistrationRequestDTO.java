package org.psw_isa.psw_isa_backend.dtos;

import java.time.LocalDateTime;

import org.psw_isa.psw_isa_backend.models.RegistrationRequest;

public class RegistrationRequestDTO {

	private Long id;
	private boolean approved;
	private LocalDateTime time;
	private boolean rejected;
	
	
	
	public LocalDateTime getTime() {
		return time;
	}
	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public boolean isApproved() {
		return approved;
	}
	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	
	
	public RegistrationRequestDTO(RegistrationRequest registrationRequest) {
		this(registrationRequest.getId(), registrationRequest.getApproved(), registrationRequest.getTime(),registrationRequest.getRejected());   
	}
	
	public RegistrationRequestDTO(Long id, boolean approved, LocalDateTime time,boolean rejected) {
		super();
		this.id = id;
		this.approved = approved;
		this.time = time;
		this.rejected=rejected;
	}
	public RegistrationRequestDTO() {
		
	}
	public boolean isRejected() {
		return rejected;
	}
	public void setRejected(boolean rejected) {
		this.rejected = rejected;
	}
	
	
	
	
	
}
