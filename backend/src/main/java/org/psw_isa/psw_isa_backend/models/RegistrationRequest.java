package org.psw_isa.psw_isa_backend.models;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import java.time.*;


@Entity
public class RegistrationRequest {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

	

	private LocalDateTime time; 
	
	
	private Boolean approved; 
	
	private Boolean rejected;
	

	public RegistrationRequest() 
	{
	}
	public RegistrationRequest(LocalDateTime _time, Boolean _approved, Boolean rejected) {
		super();
		 

		this.time = _time;
		 
		this.approved = _approved;
		
		this.rejected= rejected;
		
	}
	
	 
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public LocalDateTime getTime() 
	{
		return this.time;
	}

	public void setTime(LocalDateTime newValue) 
	{
		this.time = newValue;
	}
	 
	public Boolean getApproved() 
	{
		return this.approved;
	}

	public void setApproved(Boolean newValue) 
	{
		this.approved = newValue;
	}
	public Boolean getRejected() {
		return rejected;
	}
	public void setRejected(Boolean rejected) {
		this.rejected = rejected;
	}
	
}