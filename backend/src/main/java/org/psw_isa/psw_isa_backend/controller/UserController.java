package org.psw_isa.psw_isa_backend.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.server.PathParam;

import org.psw_isa.psw_isa_backend.dtos.RegistrationDTO;
import org.psw_isa.psw_isa_backend.models.User;
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
@RequestMapping(value = "users")
public class UserController {

	
	@Autowired
	private UserService userService;
	
	
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<User> findOneById(@PathVariable("id") Long id){
		
		User user = userService.findOneByid(id);
		
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@PostMapping(value = "/logOut")
	public ResponseEntity<Long> logOut(){
		userService.logOut();
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@PostMapping(value = "/", consumes = "application/json")
	public ResponseEntity<Long> save(@RequestBody RegistrationDTO data){

		// validation 
		if (data.getFirstName() == null || data.getFirstName().equals("")) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		if (data.getLastName() == null || data.getLastName().equals("")) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		if (data.getEmail() == null || data.getEmail().equals("")) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		if (data.getPassword() == null || data.getPassword().equals("")) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		if (! data.getPassword().equals(data.getPassword2())) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		if (! data.getEmail().contains("@")) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		if (data.getMobilePhone() == null || data.getMobilePhone().equals("")) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		User user = new User();
		user.setFirstName(data.getFirstName());
		user.setLastName(data.getLastName());
		user.setEmail(data.getEmail());
		user.setPassword(data.getPassword());
		user.setAddress(data.getAddress());
		user.setBirthday(data.getBirthday());
		user.setMobilePhone(data.getMobilePhone());
		userService.save(user);
		return new ResponseEntity<>(null, HttpStatus.OK);
	}
	
	
}
