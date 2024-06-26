package org.psw_isa.psw_isa_backend.dtos;

import java.time.LocalDate;

public class RegistrationDTO {

	private String firstname;
	private String lastname;
	private String email;
	private String mobile_phone;
	private String password;
	private String password2;
	private String address;
	private LocalDate birthday;
	
	public RegistrationDTO(String firstname, String lastname, String email, String password, String password2, String address,
			String mobile_phone, LocalDate birthday) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.mobile_phone = mobile_phone;
		this.password = password;
		this.password2 = password2;
		this.address = address;
		this.birthday = birthday;
	}

	public String getFirstName() {
		return firstname;
	}

	public void setFirstName(String name) {
		this.firstname = name;
	}

	public String getLastName() {
		return lastname;
	}

	public void setLastName(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	} 
	public String getMobilePhone() {
		return mobile_phone;
	}

	public void setMobilePhone(String mobile_phone) {
		this.mobile_phone = mobile_phone;
	}

	
}
