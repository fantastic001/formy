package org.psw_isa.psw_isa_backend.models.widgets.short_answer;





import org.psw_isa.psw_isa_backend.models.FormItemAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortAnswerRepository extends JpaRepository<ShortAnswer, Long> {

	public ShortAnswer findOneById(Long id);

	public ShortAnswer save(ShortAnswer answer);
	
}
