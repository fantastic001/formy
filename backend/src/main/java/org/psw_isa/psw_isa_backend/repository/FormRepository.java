package org.psw_isa.psw_isa_backend.repository;





import org.psw_isa.psw_isa_backend.models.Form;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormRepository extends JpaRepository<Form, Long> {

	public Form findOneByid(Long id);

	public Form save(Form form);
	
}
