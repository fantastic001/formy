package org.psw_isa.psw_isa_backend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.psw_isa.psw_isa_backend.models.Form;


import org.psw_isa.psw_isa_backend.models.User;


import org.psw_isa.psw_isa_backend.repository.FormRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.psw_isa.psw_isa_backend.dtos.FormDTO;

@Service
public class FormService {

	@Autowired
	FormRepository formRepository;

	@Autowired
	MeService meService; 

	public List<Form> findAll() {
		return formRepository.findAll();
	}


	public Form findOneByid(Long id) {
		return formRepository.findOneByid(id);
	}

	public Form save(FormDTO formDTO) {
		User myself = meService.greeting();
		return formRepository.save(new Form(
			LocalDateTime.now(), 
			formDTO.getSubmissionExpiryTime(),
			myself, 
			formDTO.getName(), 
			formDTO.getDescription()
		));
	}


	public Form update(Long id, FormDTO form) {
		Optional<Form> myformResult = formRepository.findById(id);
		if (! myformResult.isPresent())  {
			return null; 
		} else {
			Form myform = myformResult.get();
			return formRepository.save(new Form(
				myform.getCreateTime(), 
				form.getSubmissionExpiryTime(), 
				myform.getAuthor(), 
				form.getName(), 
				form.getDescription()
			));
		}
		
	}

}
