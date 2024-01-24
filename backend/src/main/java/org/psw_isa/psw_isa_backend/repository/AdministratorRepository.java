package org.psw_isa.psw_isa_backend.repository;

import java.util.List;

import org.psw_isa.psw_isa_backend.models.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdministratorRepository extends JpaRepository<Administrator, Long> {

	
	Administrator findOneByid(Long id);
	
	List<Administrator> findAll();

	// https://docs.oracle.com/cd/E17904_01/apirefs.1111/e13946/ejb3_langref.html
	@Query(value = "SELECT a from Administrator a LEFT JOIN FETCH a.user u WHERE u.email = ?1")
	Administrator findByEmail(String email);
	
}
