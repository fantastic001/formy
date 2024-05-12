package org.psw_isa.psw_isa_backend.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.psw_isa.psw_isa_backend.dtos.LogInDTO;
import org.psw_isa.psw_isa_backend.models.User;
import org.psw_isa.psw_isa_backend.repository.UserRepository;
import org.psw_isa.psw_isa_backend.service.CheckRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import org.psw_isa.psw_isa_backend.Logger;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	CheckRoleService checkRoleService;


	public User findOneByemail(String email) {
		return userRepository.findOneByemail(email);
	}

	public User findOneByid(Long id) {
		return userRepository.findOneByid(id);
	}

	public String hashPassword(String password) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		md.update(password.getBytes());
		byte[] digest = md.digest();
		StringBuffer sb = new StringBuffer();
		for (byte b : digest) {
			sb.append(String.format("%02x", b & 0xff));
		}
		return sb.toString();
	}

	public int updateUser(String firstname, String lastname, String address, LocalDate birthday, String mobile_phone,
			String password, Long id) {
		password = hashPassword(password);
		return userRepository.updateUser(firstname, lastname, address, birthday, mobile_phone, password, id);
	}

	public User save(User user) {
		user.setPassword(hashPassword(user.getPassword()));
		return userRepository.save(user);
	}

	public void logOut() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);
		session.invalidate();
	}

	public int login(LogInDTO loginData) {
		String mypass = hashPassword(loginData.getPassword());
		User user = userRepository.findOneByemail(loginData.getEmail());
		if (user != null) {
			Long id = user.getId();

			// show passwords 
			Logger.getInstance().debug("Password: " + mypass);
			Logger.getInstance().debug("Password from db: " + user.getPassword());

			if (mypass.equals(user.getPassword())) {
				ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder
				.currentRequestAttributes();
				HttpSession session = attr.getRequest().getSession(true);

				session.setAttribute("user", user.getEmail());
				return 1;

			} else {

				Logger.getInstance().debug("Password incorrect");
				return 0;
			}
		} else {
			Logger.getInstance().warning("user with this email is not found");
			return 0;
		}
	}

}
