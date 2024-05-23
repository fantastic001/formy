package org.psw_isa.psw_isa_backend.controller;

import org.psw_isa.psw_isa_backend.service.CheckRoleService;
import org.psw_isa.psw_isa_backend.service.FormService;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.psw_isa.psw_isa_backend.models.Form;
import org.psw_isa.psw_isa_backend.models.FormItem;
import org.psw_isa.psw_isa_backend.Logger;
import org.psw_isa.psw_isa_backend.dtos.FormDTO;
import org.psw_isa.psw_isa_backend.dtos.ItemDTO;
import org.psw_isa.psw_isa_backend.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "forms")
public class FormController {
	
	@Autowired 
	FormService formService;

	@Autowired
	CheckRoleService checkRoleService;
	
	
	@GetMapping()
	public ResponseEntity<List<Form>> findAll(){
		// get currently logged in user 
		User currentLoggedInUser = checkRoleService.getUser();
		if (currentLoggedInUser == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		List<Form> forms = formService.findAll();
		// filter out forms by this user 
		forms = forms.stream().filter(form -> form.getAuthor().getId() == currentLoggedInUser.getId()).collect(Collectors.toList());
		return new ResponseEntity<>(forms, HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Form> findOneByid(@PathVariable("id") Long id){
		User currentLoggedInUser = checkRoleService.getUser();
		if (currentLoggedInUser == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		Form form = formService.findOneByid(id);
		if (form.getAuthor().getId() != currentLoggedInUser.getId()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<>(formService.findOneByid(id), HttpStatus.OK);
	}

	@GetMapping(value="/{id}/data")
	public ResponseEntity<HashMap<String, Object>> getData(@PathVariable("id") Long id){
		HashMap<String, Object> data = new HashMap<>();
		data.put("test", "test");
		HashMap<String, Object> data2 = new HashMap<>();
		data2.put("test2", 2);
		data.put("test2", data2);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	

	@PostMapping(value="/{id}")
	public ResponseEntity<Form> update(@PathVariable("id") Long id, @RequestBody FormDTO form){
		User currentLoggedInUser = checkRoleService.getUser();
		if (currentLoggedInUser == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		Form formToUpdate = formService.findOneByid(id);
		if (formToUpdate.getAuthor().getId() != currentLoggedInUser.getId()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<>(formService.update(id, form), HttpStatus.OK);
	}

	@PostMapping(consumes = "application/json")
	public ResponseEntity<Long> save(@RequestBody FormDTO formDTO){
		
		Logger.getInstance().debug("Creating form with name " + formDTO.getName());;
		Form form = formService.save(formDTO);
		return new ResponseEntity<>(form.getId(),HttpStatus.OK);
	}

	@DeleteMapping(value="/{id}")
	public ResponseEntity<Long> delete(@PathVariable("id") Long id){
		User currentLoggedInUser = checkRoleService.getUser();
		if (currentLoggedInUser == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		Form form = formService.findOneByid(id);
		if (form.getAuthor().getId() != currentLoggedInUser.getId()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		formService.delete(id);
		return new ResponseEntity<>(id, HttpStatus.OK);
	}

	@PostMapping(value="/{id}/addFormItem")
	public ResponseEntity<FormItem> addFormItem(@PathVariable("id") Long id, @RequestBody ItemDTO itemDTO){
		Logger.getInstance().debug("Adding form item to form with id " + id);
		// print form item params 
		Logger.getInstance().debug("Name: " + itemDTO.getName());
		Logger.getInstance().debug("Description: " + itemDTO.getDescription());
		Logger.getInstance().debug("Type: " + itemDTO.getType());
		// print each data entry 
		HashMap<String, String> data = itemDTO.getData();
		for (String key : data.keySet()) {
			Logger.getInstance().debug("Data key: " + key + " value: " + data.get(key));
		}
		User currentLoggedInUser = checkRoleService.getUser();
		if (currentLoggedInUser == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		Form form = formService.findOneByid(id);
		if (form.getAuthor().getId() != currentLoggedInUser.getId()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		FormItem result = formService.addFormItem(id, itemDTO.getName(), itemDTO.getDescription(), itemDTO.getType(), itemDTO.getData());
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping(value="/{id}/formItems")
	public ResponseEntity<List<FormItem>> getFormItems(@PathVariable("id") Long id){
		User currentLoggedInUser = checkRoleService.getUser();
		if (currentLoggedInUser == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		Form form = formService.findOneByid(id);
		if (form.getAuthor().getId() != currentLoggedInUser.getId()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<>(formService.getFormItems(id), HttpStatus.OK);
	}

}
