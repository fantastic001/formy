package org.psw_isa.psw_isa_backend.service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.naming.factory.SendMailFactory;
import org.psw_isa.psw_isa_backend.dtos.EmailDTO;
import org.psw_isa.psw_isa_backend.dtos.RegistrationDTO;
import org.psw_isa.psw_isa_backend.dtos.RegistrationRequestDTO;
import org.psw_isa.psw_isa_backend.models.RegistrationRequest;
import org.psw_isa.psw_isa_backend.models.User;

import org.psw_isa.psw_isa_backend.repository.RegistrationRequestRepository;
import org.psw_isa.psw_isa_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class RegistrationRequestService {

	@Autowired
	private RegistrationRequestRepository registrationRequestRepository;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private HashService hashService;
	
	@Autowired
	private SendEmailService sendEmailService;
	
	public List<RegistrationRequestDTO> findAllNotApproved(){
		List<RegistrationRequest> registrationRequests = registrationRequestRepository.findAll();
		List<RegistrationRequestDTO> registrationRequestDTOs = new ArrayList();
		
		for (RegistrationRequest registrationRequest : registrationRequests){
			if(!registrationRequest.getApproved()) {
				
				if(!registrationRequest.getRejected())
				registrationRequestDTOs.add(new RegistrationRequestDTO(registrationRequest));
			}
		}
		
		return registrationRequestDTOs;
	}
	
	public List<RegistrationRequest> findAll(){
		return registrationRequestRepository.findAll();
	}
	
	public RegistrationRequest save (RegistrationRequest registrationRequest) {
		return registrationRequestRepository.save(registrationRequest);
	}
	
	public RegistrationRequest newRegistrationRequest(RegistrationDTO registrationDTO) {
		User user = new User(registrationDTO.getFirstname(), registrationDTO.getLastname(), registrationDTO.getMobile_phone(), registrationDTO.getEmail(), registrationDTO.getAddress(), registrationDTO.getBirthday(), registrationDTO.getPassword(), 0);
				
		User validator = userRepository.findOneByemail(registrationDTO.getEmail());
		if(validator == null) {
			if(registrationDTO.getPassword().equals(registrationDTO.getPassword2())) {
				userRepository.save(user);				
				RegistrationRequest registrationRequest = new RegistrationRequest();
				registrationRequest.setApproved(true);
				registrationRequest.setRejected(false);
				registrationRequest.setTime(LocalDateTime.now());
				registrationRequestRepository.save(registrationRequest);
				
				
				return registrationRequest;
			} else {
				return null;
			}
		} else {
			return null;
		}
		
	}
	


	
}
