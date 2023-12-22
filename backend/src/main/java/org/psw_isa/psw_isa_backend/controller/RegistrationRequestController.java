package org.psw_isa.psw_isa_backend.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.server.PathParam;

import org.psw_isa.psw_isa_backend.dtos.RegistrationDTO;
import org.psw_isa.psw_isa_backend.dtos.RegistrationRequestDTO;
import org.psw_isa.psw_isa_backend.models.RegistrationRequest;
import org.psw_isa.psw_isa_backend.models.User;
import org.psw_isa.psw_isa_backend.repository.RegistrationRequestRepository;
import org.psw_isa.psw_isa_backend.service.RegistrationRequestService;
import org.psw_isa.psw_isa_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "registrationRequests")
public class RegistrationRequestController {

	
	@Autowired
	private RegistrationRequestService registrationRequestService;
	
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<RegistrationRequest> save(@RequestBody RegistrationDTO registrationDTO){
		
		if(registrationRequestService.newRegistrationRequest(registrationDTO) == null) {
			return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
		} else {
			return new ResponseEntity<>(registrationRequestService.newRegistrationRequest(registrationDTO), HttpStatus.OK);
		}
	}
	
	
}
