package org.psw_isa.psw_isa_backend.repository;





import org.psw_isa.psw_isa_backend.models.FormItemAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormItemAnswerRepository extends JpaRepository<FormItemAnswer, Long> {

	public FormItemAnswer findOneById(Long id);

	public FormItemAnswer save(FormItemAnswer answer);
	
}
