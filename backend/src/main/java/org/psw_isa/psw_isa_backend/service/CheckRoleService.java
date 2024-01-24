package org.psw_isa.psw_isa_backend.service;

import javax.servlet.http.HttpSession;


import org.psw_isa.psw_isa_backend.models.User;
import org.psw_isa.psw_isa_backend.repository.AdministratorRepository;
import org.psw_isa.psw_isa_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class CheckRoleService {
	
	@Autowired
	AdministratorRepository adminRepository;
	
	@Autowired 
	UserRepository userRepository;
	
	
	public String getEmail() 
	{
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes(); 
		HttpSession session = attr.getRequest().getSession(true); 
		
		String loggedEmail = (String) session.getAttribute("user");
		return loggedEmail;
	}

	public User getUser() 
	{
		String loggedEmail = getEmail();

		User loggedUser = userRepository.findOneByemail(loggedEmail);
		return loggedUser;
	}

	public boolean checkIfLogged() {
		return getUser() != null;
	}


	
	
	public boolean checkIfAdministrator() {
		return checkIfLogged() && adminRepository.findByEmail(getEmail()) != null;
	}
	
	
}
