package org.psw_isa.psw_isa_backend.models.widgets.multiple_choice;





import org.springframework.data.jpa.repository.JpaRepository;

public interface MultipleChoiceRepository extends JpaRepository<MultipleChoice, Long> {

	public MultipleChoice findOneById(Long id);

	public MultipleChoice save(MultipleChoice answer);
	
}
