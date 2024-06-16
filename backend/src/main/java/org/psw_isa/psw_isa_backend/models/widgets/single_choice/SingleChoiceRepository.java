package org.psw_isa.psw_isa_backend.models.widgets.single_choice;





import org.springframework.data.jpa.repository.JpaRepository;

public interface SingleChoiceRepository extends JpaRepository<SingleChoice, Long> {

	public SingleChoice findOneById(Long id);

	public SingleChoice save(SingleChoice answer);
	
}
