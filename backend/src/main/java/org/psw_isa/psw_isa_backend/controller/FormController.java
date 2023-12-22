package org.psw_isa.psw_isa_backend.controller;

import org.psw_isa.psw_isa_backend.service.FormService;


import java.util.List;

import org.psw_isa.psw_isa_backend.models.Form;
import org.psw_isa.psw_isa_backend.dtos.FormDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "care")
public class FormController {
	
	@Autowired 
	FormService formService;
	
	
	@GetMapping()
	public ResponseEntity<List<Form>> findAll(){
		return new ResponseEntity<>(formService.findAll(), HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Form> findOneByid(@PathVariable("id") Long id){
		return new ResponseEntity<>(formService.findOneByid(id), HttpStatus.OK);
	}
	

	@PostMapping(value="/{id}")
	public ResponseEntity<Form> update(@PathVariable("id") Long id, @RequestBody FormDTO form){
		return new ResponseEntity<>(formService.update(id, form), HttpStatus.OK);
	}

	@PostMapping(consumes = "application/json")
	public ResponseEntity<Long> save(@RequestBody FormDTO formDTO){
		
		Form form = formService.save(formDTO);
		return new ResponseEntity<>(form.getId(),HttpStatus.OK);
	}

}
