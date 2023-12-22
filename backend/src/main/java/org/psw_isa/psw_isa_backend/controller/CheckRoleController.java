package org.psw_isa.psw_isa_backend.controller;

import java.util.List;

import org.psw_isa.psw_isa_backend.service.CheckRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.psw_isa.psw_isa_backend.models.User;

@RestController
@RequestMapping(value = "checkRole")
public class CheckRoleController {
	
	@Autowired
	CheckRoleService checkRoleService;
	
	@GetMapping(value="/")
	public ResponseEntity<String> checkRole(){
		if(checkRoleService.checkIfLogged()) {
			if (checkRoleService.checkIfAdministrator()) {
				return new ResponseEntity<String>("ADMINISTRATOR", HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("USER", HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<String>("NOT_LOGGED", HttpStatus.FORBIDDEN);
		}
		
	}
	@GetMapping(value="/info")
	public ResponseEntity<User> info(){
		return new ResponseEntity<>(checkRoleService.getUser(), HttpStatus.OK);
	}

}
